package uk.laxd.homeassistantclient.model.domain.entity

import jakarta.inject.Inject
import jakarta.inject.Named
import uk.laxd.homeassistantclient.model.domain.entity.light.LightEntity
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
    <E extends Entity> E createEntity(HomeAssistantEntity homeAssistantEntity) {
        switch (homeAssistantEntity.domain) {
            case "light":
                return createLightEntity(homeAssistantEntity) as E
            default:
                return new GenericEntity(webSocketClient, homeAssistantEntity) as E
        }
    }

    @Override
    LightEntity createLightEntity(HomeAssistantEntity homeAssistantEntity) {
        new LightEntity(webSocketClient, homeAssistantEntity)
    }

}
