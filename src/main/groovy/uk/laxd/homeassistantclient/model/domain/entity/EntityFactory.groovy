package uk.laxd.homeassistantclient.model.domain.entity

import uk.laxd.homeassistantclient.client.HomeAssistantClient
import uk.laxd.homeassistantclient.client.exception.InvalidEntityException
import uk.laxd.homeassistantclient.model.domain.entity.helpers.InputNumber
import uk.laxd.homeassistantclient.model.domain.entity.light.LightEntity
import uk.laxd.homeassistantclient.model.json.HomeAssistantEntity

interface EntityFactory {

    /**
     * Creates an {@link Entity} that matches the given {@link HomeAssistantEntity} passed in.
     *
     * The type of this will match the domain of the {@link HomeAssistantEntity}'s entity ID e.g.
     * calling this with a {@link HomeAssistantEntity} with an entity ID of "light.kitchen" will
     * return a {@link uk.laxd.homeassistantclient.model.domain.entity.light.LightEntity}
     * @param client {@link HomeAssistantClient} to inject into created {@link Entity}
     * @param homeAssistantEntity entity from HA to construct this entity from
     * @return An {@link Entity} matching the domain of the given entity ID. Returns a {@link BaseEntity} if
     *      the domain is not recognised or implemented.
     */
    <E extends Entity> E createEntity(HomeAssistantClient client, HomeAssistantEntity homeAssistantEntity)

    /**
     * Creates a new {@link uk.laxd.homeassistantclient.model.domain.entity.light.LightEntity} from the given
     * {@link HomeAssistantEntity}
     * @param client {@link HomeAssistantClient} to inject into created entity
     * @throws InvalidEntityException if the {@link HomeAssistantEntity} is not a "light" domain entity.
     */
    LightEntity createLightEntity(HomeAssistantClient client, HomeAssistantEntity homeAssistantEntity)
            throws InvalidEntityException

    /**
     * Creates a new {@link uk.laxd.homeassistantclient.model.domain.entity.helpers.InputNumber} from the given
     * {@link HomeAssistantEntity}
     * @param client {@link HomeAssistantClient} to inject into created entity
     * @throws InvalidEntityException if the {@link HomeAssistantEntity} is not a "light" domain entity.
     */
    InputNumber createInputNumber(HomeAssistantClient client, HomeAssistantEntity homeAssistantEntity)

}
