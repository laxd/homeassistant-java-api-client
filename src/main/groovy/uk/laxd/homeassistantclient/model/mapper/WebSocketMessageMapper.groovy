package uk.laxd.homeassistantclient.model.mapper

import jakarta.inject.Named
import uk.laxd.homeassistantclient.model.domain.response.HomeAssistantPongMessage
import uk.laxd.homeassistantclient.model.json.ws.incoming.PongWebSocketMessage

@Named
class WebSocketMessageMapper {

    HomeAssistantPongMessage map(PongWebSocketMessage webSocketMessage) {
        new HomeAssistantPongMessage(webSocketMessage.type)
    }

}
