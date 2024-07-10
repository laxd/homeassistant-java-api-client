package uk.laxd.homeassistantclient.model.domain.entity.state.converter

import uk.laxd.homeassistantclient.model.json.HomeAssistantEntity

/**
 * A no-op converter that just passes the state from the server back
 */
class StringStateConverter implements StateConverter<String> {

    @Override
    String convertState(HomeAssistantEntity entity) {
        entity?.state
    }

}
