package uk.laxd.homeassistantclient.model.domain.entity

import uk.laxd.homeassistantclient.model.domain.service.Service

interface Entity {

    /**
     * Calls the given service with only this entity as the target (any other entries are removed.
     *
     * The original {@link Service} object passed through is NOT modified
     * @param service
     */
    void callService(Service service)

    /**
     * Turns this entity on, if it supports being turned on.
     */
    void turnOn()

    /**
     * Turn the given entity on, if it supports being turned on, with some additional data
     * (see <a href="https://www.home-assistant.io/docs/scripts/service-calls/#passing-data-to-the-service-call">here</a>)
     * for additional details on what kind of data Home Assistant accepts for what entities)
     * @param additionalData
     */
    void turnOn(Map<String, Object> additionalData)

    /**
     * Turns this entity off, if it supports being turned off.
     */
    void turnOff()

    /**

     * Turn the given entity off, if it supports being turned off, with some additional data
     * (see <a href="https://www.home-assistant.io/docs/scripts/service-calls/#passing-data-to-the-service-call">here</a>)
     * for additional details on what kind of data Home Assistant accepts for what entities)
     * @param additionalData     * @param additionalData
     */
    void turnOff(Map<String, Object> additionalData)

    /**
     * Toggles the state of this entity, if it supports being toggled. An entity that is turned on will be turned off,
     * and an entity that is off will be turned on.
     */
    void toggle()

    /**
     * Toggles the state of this entity, if it supports being toggled, with some additional data.
     * An entity that is turned on will be turned off, and an entity that is off will be turned on.
     * (see <a href="https://www.home-assistant.io/docs/scripts/service-calls/#passing-data-to-the-service-call">here</a>)
     * for additional details on what kind of data Home Assistant accepts for what entities)
     */
    void toggle(Map<String, Object> additionalData)
}
