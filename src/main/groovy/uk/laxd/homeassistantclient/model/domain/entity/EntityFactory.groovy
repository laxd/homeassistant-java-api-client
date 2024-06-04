package uk.laxd.homeassistantclient.model.domain.entity

import uk.laxd.homeassistantclient.model.json.HomeAssistantEntity

interface EntityFactory {

    <E extends Entity> E createEntity(HomeAssistantEntity homeAssistantEntity)

}
