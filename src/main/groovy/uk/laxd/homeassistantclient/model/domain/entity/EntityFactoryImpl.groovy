package uk.laxd.homeassistantclient.model.domain.entity

import com.sun.org.apache.xerces.internal.dom.EntityImpl
import jakarta.inject.Inject
import jakarta.inject.Named
import org.springframework.web.socket.client.WebSocketClient
import uk.laxd.homeassistantclient.model.json.HomeAssistantEntity
import uk.laxd.homeassistantclient.ws.HomeAssistantWebSocketClient

@Named
class EntityFactoryImpl implements EntityFactory {

    private HomeAssistantWebSocketClient webSocketClient

    @Inject
    EntityFactoryImpl(HomeAssistantWebSocketClient webSocketClient) {
        this.webSocketClient = webSocketClient
    }

    @Override
    Entity createEntity(HomeAssistantEntity homeAssistantEntity) {
        new GenericEntity(webSocketClient, homeAssistantEntity)
    }
}
