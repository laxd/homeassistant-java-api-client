package uk.laxd.homeassistantclient.model.domain.entity.state.converter

import uk.laxd.homeassistantclient.model.json.HomeAssistantEntity

class NumericStateConverter implements StateConverter<Float> {

    @Override
    Float convertState(HomeAssistantEntity entity) {
        if (entity.state == null || entity.state.empty) {
            return 0f
        }

        Float.parseFloat(entity.state)
    }

}
