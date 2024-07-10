package uk.laxd.homeassistantclient.model.domain.entity.state.converter

import uk.laxd.homeassistantclient.model.domain.entity.state.State
import uk.laxd.homeassistantclient.model.json.HomeAssistantEntity

class OnOffStateConverter implements StateConverter<State> {

    @Override
    State convertState(HomeAssistantEntity entity) {
        entity.state.toLowerCase() ==~ /on|true/ ? State.ON : State.OFF
    }

}

