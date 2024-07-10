package uk.laxd.homeassistantclient.model.domain.entity.state.converter

import uk.laxd.homeassistantclient.model.json.HomeAssistantEntity

/**
 * Converts the state from a {@link HomeAssistantEntity}
 * @param <S> The type of the state to convert to
 */
interface StateConverter<S> {

    S convertState(HomeAssistantEntity entity)

}
