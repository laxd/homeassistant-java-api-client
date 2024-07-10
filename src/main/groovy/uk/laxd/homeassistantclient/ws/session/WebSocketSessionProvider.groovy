package uk.laxd.homeassistantclient.ws.session

import groovy.util.logging.Slf4j
import jakarta.inject.Inject
import jakarta.inject.Named
import org.springframework.web.socket.WebSocketSession
import org.springframework.web.socket.client.WebSocketClient
import org.springframework.web.socket.client.standard.StandardWebSocketClient
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

    private static final int TIMEOUT_SECONDS = 10
    private static final String WEBSOCKET_PROTOCOL = "ws://"
    private static final String HTTPS_PROTOCOL = "https://"
    private static final String HTTP_PROTOCOL = "http://"

    private WebSocketSession existingSession
    private boolean isAuthenticated = false

    private final HomeAssistantWebSocketHandler handler
    private final MessageResponseListener messageResponseListener
    private final JacksonWebSocketMessageConverter webSocketMessageConverter

    @Inject
    WebSocketSessionProvider(HomeAssistantWebSocketHandler handler,
                             MessageResponseListener messageResponseListener,
                             JacksonWebSocketMessageConverter webSocketMessageConverter) {
        this.handler = handler
        this.messageResponseListener = messageResponseListener
        this.webSocketMessageConverter = webSocketMessageConverter
    }

    void authenticate(String url, String token) {
        String wsUrl = url.replace(HTTPS_PROTOCOL, WEBSOCKET_PROTOCOL)
                .replace(HTTP_PROTOCOL, WEBSOCKET_PROTOCOL) + "/api/websocket"

        log.debug("Connecting to WebSocket URL: {}", wsUrl)

        // Register listener BEFORE we connect, so that we can catch the auth_required message
        def authRequiredFuture = messageResponseListener.waitForMessage(HomeAssistantAuthRequiredMessage)

        WebSocketClient client = new StandardWebSocketClient()
        this.existingSession = new LoggingWebSocketSessionDecorator(client.execute(handler, wsUrl).get())

        // Wait for auth required message
        authRequiredFuture.get(TIMEOUT_SECONDS, TimeUnit.SECONDS)

        // First register listener so we can catch auth response
        def authResponseFuture = messageResponseListener.waitForMessage(HomeAssistantAuthResponseMessage)

        // Then actually send authentication
        def authMessage = new AuthenticationWebSocketMessage(token)
        this.existingSession.sendMessage(webSocketMessageConverter.toTextMessage(authMessage))

        // Wait for auth success
        def authResponse = authResponseFuture.get(TIMEOUT_SECONDS, TimeUnit.SECONDS)

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
            throw new NotAuthenticatedException("Must be authenticated first before fetching session. " +
                    "Make sure to call WebSocketSessionProvider.authenticate first.")
        }

        this.existingSession
    }

}
