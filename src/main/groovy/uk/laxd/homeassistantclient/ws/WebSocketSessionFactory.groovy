package uk.laxd.homeassistantclient.ws

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Lazy
import org.springframework.web.socket.WebSocketSession
import org.springframework.web.socket.client.WebSocketClient
import org.springframework.web.socket.client.standard.StandardWebSocketClient
import uk.laxd.homeassistantclient.client.HomeAssistantAuthentication

@Configuration
@Lazy
class WebSocketSessionFactory {

    private static final Logger logger = LoggerFactory.getLogger(WebSocketSessionFactory.class)

    @Bean
    WebSocketSession createWebSocketSession(HomeAssistantAuthentication auth, HomeAssistantWebSocketHandler handler) {
        WebSocketClient client = new StandardWebSocketClient()

        String wsUrl = auth.url.replace("https://", "ws://")
        .replace("http://", "ws://") + "/api/websocket"

        logger.debug("Connecting to WebSocket URL: {}", wsUrl)

        return new LoggingWebSocketSessionDecorator(client.execute(handler, wsUrl).get())
    }
}

