package uk.laxd.homeassistantclient.ws.message.model

import com.fasterxml.jackson.databind.ObjectMapper
import jakarta.inject.Inject
import jakarta.inject.Named
import org.springframework.web.socket.TextMessage

@Named
class JacksonWebSocketMessageConverter {

    private ObjectMapper objectMapper

    @Inject
    JacksonWebSocketMessageConverter(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper
    }

    TextMessage toTextMessage(Object object) {
        def stringMessage = objectMapper.writeValueAsString(object)

        new TextMessage(stringMessage)
    }
}
