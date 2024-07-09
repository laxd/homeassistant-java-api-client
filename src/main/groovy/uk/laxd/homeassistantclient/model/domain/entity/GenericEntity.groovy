package uk.laxd.homeassistantclient.model.domain.entity

import uk.laxd.homeassistantclient.model.domain.entity.state.converter.StringStateConverter
import uk.laxd.homeassistantclient.model.json.HomeAssistantEntity
import uk.laxd.homeassistantclient.ws.HomeAssistantWebSocketClient

class GenericEntity extends AbstractEntity<String> {
    GenericEntity(HomeAssistantWebSocketClient wsClient, HomeAssistantEntity entity) {
        super(wsClient, entity, new StringStateConverter())
    }
}
