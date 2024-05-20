package uk.laxd.homeassistantclient.model.trigger

import com.fasterxml.jackson.annotation.JsonValue

enum TriggerType {
    EVENT("not_implemented"),
    HOME_ASSISTANT("not_implemented"),
    MQTT("not_implemented"),
    NUMERIC_STATE("not_implemented"),
    STATE("state"),
    SUN("not_implemented"),
    TAG("not_implemented"),
    TEMPLATE("not_implemented"),
    TIME("time"),
    TIME_PATTERN("not_implemented"),
    PERSISTENT_NOTIFICATION("not_implemented"),
    WEBHOOK("not_implemented"),
    ZONE("not_implemented"),
    GEO_LOCATION("not_implemented"),
    CALENDAR("not_implemented"),
    SENTENCE("not_implemented")

    @JsonValue
    String string

    TriggerType(String string) {
        this.string = string
    }
}