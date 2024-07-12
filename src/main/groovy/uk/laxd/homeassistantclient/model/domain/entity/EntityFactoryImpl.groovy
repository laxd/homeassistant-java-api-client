package uk.laxd.homeassistantclient.model.domain.entity

import jakarta.inject.Named
import uk.laxd.homeassistantclient.client.HomeAssistantClient
import uk.laxd.homeassistantclient.model.domain.entity.helpers.InputNumber
import uk.laxd.homeassistantclient.model.domain.entity.light.LightEntity
import uk.laxd.homeassistantclient.model.domain.entity.state.converter.NumericStateConverter
import uk.laxd.homeassistantclient.model.domain.entity.state.converter.OnOffStateConverter
import uk.laxd.homeassistantclient.model.json.HomeAssistantEntity

@Named
class EntityFactoryImpl implements EntityFactory {

    @Override
    <E extends Entity> E createEntity(HomeAssistantClient client, HomeAssistantEntity homeAssistantEntity) {
        switch (homeAssistantEntity.domain) {
            case "light":
                return createLightEntity(client, homeAssistantEntity) as E
            case "input_number":
                return createInputNumber(client, homeAssistantEntity) as E
            default:
                return new GenericEntity(client, homeAssistantEntity) as E
        }
    }

    @Override
    LightEntity createLightEntity(HomeAssistantClient client, HomeAssistantEntity homeAssistantEntity) {
        new LightEntity(client, homeAssistantEntity, new OnOffStateConverter())
    }

    @Override
    InputNumber createInputNumber(HomeAssistantClient client, HomeAssistantEntity homeAssistantEntity) {
        new InputNumber(client, homeAssistantEntity, new NumericStateConverter())
    }

}
