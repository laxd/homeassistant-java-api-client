package uk.laxd.homeassistantclient.model.domain.trigger

enum TriggerType {

    EVENT(NOT_IMPLEMENTED),
    HOME_ASSISTANT(NOT_IMPLEMENTED),
    MQTT(NOT_IMPLEMENTED),
    NUMERIC_STATE("numeric_state"),
    STATE("state"),
    SUN(NOT_IMPLEMENTED),
    TAG(NOT_IMPLEMENTED),
    TEMPLATE("template"),
    TIME("time"),
    TIME_PATTERN("time_pattern"),
    PERSISTENT_NOTIFICATION(NOT_IMPLEMENTED),
    WEBHOOK(NOT_IMPLEMENTED),
    ZONE(NOT_IMPLEMENTED),
    GEO_LOCATION(NOT_IMPLEMENTED),
    CALENDAR(NOT_IMPLEMENTED),
    SENTENCE(NOT_IMPLEMENTED)
    private static final String NOT_IMPLEMENTED = "not_implemented"

    String string

    TriggerType(String string) {
        this.string = string
    }

}
