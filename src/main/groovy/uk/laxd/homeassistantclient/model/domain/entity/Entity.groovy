package uk.laxd.homeassistantclient.model.domain.entity

interface Entity {

    void turnOn()
    // TODO: Introduce e.g. an execute() method that takes a ServiceCall object?

    /**
     * Turn the given entity on, with some additional data (see <a href="https://www.home-assistant.io/docs/scripts/service-calls/#passing-data-to-the-service-call">here</a>)
     * for additional details on what kind of data Home Assistant accepts for what entities)
     * @param additionalData
     */
    void turnOn(Map<String, Object> additionalData)
    void turnOff()
    void turnOff(Map<String, Object> additionalData)
    void toggle()
    void toggle(Map<String, Object> additionalData)
}
