package uk.laxd.homeassistantclient.model.domain.entity

import uk.laxd.homeassistantclient.client.HomeAssistantClient
import uk.laxd.homeassistantclient.model.domain.entity.state.converter.StringStateConverter
import uk.laxd.homeassistantclient.model.json.HomeAssistantEntity

class GenericEntity extends BaseEntity<String> {

    GenericEntity(HomeAssistantClient client, HomeAssistantEntity entity) {
        super(client, entity, new StringStateConverter())
    }

}
