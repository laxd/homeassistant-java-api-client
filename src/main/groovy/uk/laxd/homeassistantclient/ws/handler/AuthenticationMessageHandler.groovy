package uk.laxd.homeassistantclient.ws.handler

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import org.springframework.web.socket.WebSocketSession
import uk.laxd.homeassistantclient.client.HomeAssistantAuthenticationProvider
import uk.laxd.homeassistantclient.model.json.ws.HomeAssistantAuthRequiredMessage
import uk.laxd.homeassistantclient.model.json.ws.HomeAssistantWebSocketMessage
import uk.laxd.homeassistantclient.ws.IdGenerator
import uk.laxd.homeassistantclient.ws.message.model.AuthenticationWebSocketMessage
import uk.laxd.homeassistantclient.ws.message.model.JacksonWebSocketMessageConverter

@Component
class AuthenticationMessageHandler implements MessageHandler<HomeAssistantAuthRequiredMessage> {

    private final HomeAssistantAuthenticationProvider authenticationProvider
    private final JacksonWebSocketMessageConverter messageConverter
    private final JacksonWebSocketMessageConverter webSocketMessageConverter

    @Autowired
    AuthenticationMessageHandler(HomeAssistantAuthenticationProvider authenticationProvider, JacksonWebSocketMessageConverter messageConverter, JacksonWebSocketMessageConverter webSocketMessageConverter) {
        this.authenticationProvider = authenticationProvider
        this.messageConverter = messageConverter
        this.webSocketMessageConverter = webSocketMessageConverter
    }

    @Override
    void handle(WebSocketSession session, HomeAssistantAuthRequiredMessage message) throws Exception {
        // Authentication is required, send auth message
        def authMessage = new AuthenticationWebSocketMessage(authenticationProvider.authentication.token)

        // NOTE: Unable to use MessageDispatcher here as that results in a circular reference, so just
        // handling this manually
        session.sendMessage(webSocketMessageConverter.toTextMessage(authMessage))
    }

    boolean canHandle(HomeAssistantWebSocketMessage message) {
        return message instanceof HomeAssistantAuthRequiredMessage
    }
}