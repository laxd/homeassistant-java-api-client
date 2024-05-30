package uk.laxd.homeassistantclient.ws.session

import groovy.util.logging.Slf4j
import jakarta.inject.Inject
import jakarta.inject.Named
import org.springframework.web.socket.WebSocketSession
import org.springframework.web.socket.client.WebSocketClient
import org.springframework.web.socket.client.standard.StandardWebSocketClient
import uk.laxd.homeassistantclient.client.HomeAssistantAuthenticationProvider
import uk.laxd.homeassistantclient.ws.LoggingWebSocketSessionDecorator
import uk.laxd.homeassistantclient.ws.handler.HomeAssistantWebSocketHandler
import uk.laxd.homeassistantclient.ws.message.WebSocketAuthenticationSync

@Named
@Slf4j
class WebSocketSessionProvider {

    private WebSocketAuthenticationSync authSync
    private WebSocketSession existingSession
    private HomeAssistantAuthenticationProvider authenticationProvider
    private HomeAssistantWebSocketHandler handler

    @Inject
    WebSocketSessionProvider(WebSocketAuthenticationSync authSync, HomeAssistantAuthenticationProvider authenticationProvider, HomeAssistantWebSocketHandler handler) {
        this.authSync = authSync
        this.authenticationProvider = authenticationProvider
        this.handler = handler
    }

    WebSocketSession getOrCreateAuthenticatedSession() {
        if ((!existingSession || !existingSession.isOpen())) {
            WebSocketClient client = new StandardWebSocketClient()

            def auth = authenticationProvider.getAuthentication()

            String wsUrl = auth.url.replace("https://", "ws://")
                    .replace("http://", "ws://") + "/api/websocket"

            log.debug("Connecting to WebSocket URL: {}", wsUrl)

            this.existingSession = new LoggingWebSocketSessionDecorator(client.execute(handler, wsUrl).get())

            while(!authSync.isAuthenticated()) {
                log.debug("Waiting for authentication response...")
                synchronized (authSync.sync) {
                    authSync.getSync().wait(1000)
                }
            }

        }

        return this.existingSession
    }

}
