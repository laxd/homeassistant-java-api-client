package uk.laxd.homeassistantclient.ws.session

import groovy.util.logging.Slf4j
import jakarta.inject.Inject
import jakarta.inject.Named
import org.springframework.web.socket.WebSocketSession
import org.springframework.web.socket.client.WebSocketClient
import org.springframework.web.socket.client.standard.StandardWebSocketClient
import uk.laxd.homeassistantclient.client.HomeAssistantAuthenticationProvider
import uk.laxd.homeassistantclient.model.json.ws.incoming.auth.HomeAssistantAuthFailedMessage
import uk.laxd.homeassistantclient.model.json.ws.incoming.auth.HomeAssistantAuthRequiredMessage
import uk.laxd.homeassistantclient.model.json.ws.incoming.auth.HomeAssistantAuthResponseMessage
import uk.laxd.homeassistantclient.model.json.ws.outgoing.AuthenticationWebSocketMessage
import uk.laxd.homeassistantclient.ws.LoggingWebSocketSessionDecorator
import uk.laxd.homeassistantclient.ws.MessageResponseListener
import uk.laxd.homeassistantclient.ws.exception.AuthenticationFailureException
import uk.laxd.homeassistantclient.ws.exception.NotAuthenticatedException
import uk.laxd.homeassistantclient.ws.handler.HomeAssistantWebSocketHandler

import uk.laxd.homeassistantclient.ws.message.model.JacksonWebSocketMessageConverter

import java.util.concurrent.TimeUnit

@Named
@Slf4j
class WebSocketSessionProvider {
    private WebSocketSession existingSession
    private boolean isAuthenticated = false

    private final HomeAssistantAuthenticationProvider authenticationProvider
    private final HomeAssistantWebSocketHandler handler
    private final MessageResponseListener messageResponseListener
    private final JacksonWebSocketMessageConverter webSocketMessageConverter

    @Inject
    WebSocketSessionProvider(HomeAssistantAuthenticationProvider authenticationProvider, HomeAssistantWebSocketHandler handler, MessageResponseListener messageResponseListener, JacksonWebSocketMessageConverter webSocketMessageConverter) {
        this.authenticationProvider = authenticationProvider
        this.handler = handler
        this.messageResponseListener = messageResponseListener
        this.webSocketMessageConverter = webSocketMessageConverter
    }

    void authenticate(String url, String token) {
        String wsUrl = url.replace("https://", "ws://")
                .replace("http://", "ws://") + "/api/websocket"

        log.debug("Connecting to WebSocket URL: {}", wsUrl)

        // Register listener BEFORE we connect, so that we can catch the auth_required message
        def authRequiredFuture = messageResponseListener.waitForMessage(HomeAssistantAuthRequiredMessage)

        WebSocketClient client = new StandardWebSocketClient()
        this.existingSession = new LoggingWebSocketSessionDecorator(client.execute(handler, wsUrl).get())

        // Wait for auth required message
        authRequiredFuture.get(10, TimeUnit.SECONDS)

        // First register listener so we can catch auth response
        def authResponseFuture = messageResponseListener.waitForMessage(HomeAssistantAuthResponseMessage)

        // Then actually send authentication
        def authMessage = new AuthenticationWebSocketMessage(token)
        this.existingSession.sendMessage(webSocketMessageConverter.toTextMessage(authMessage))

        // Wait for auth success
        def authResponse = authResponseFuture.get(10, TimeUnit.SECONDS)

        if (authResponse.successful) {
            log.info("Authenticated successfully")
            this.isAuthenticated = true
        }
        else {
            def authFailedResponse = authResponse as HomeAssistantAuthFailedMessage
            log.error("Authentication failed: ${authFailedResponse.message}")
            this.isAuthenticated = false
            throw new AuthenticationFailureException("Failed to authenticate with $url: ${authFailedResponse.message}")
        }
    }

    WebSocketSession getAuthenticatedSession() {
        if (!isAuthenticated) {
            throw new NotAuthenticatedException("Must be authenticated first before fetching session - Make sure to call WebSocketSessionProvider.authenticate first.")
        }

        return this.existingSession
    }

}
