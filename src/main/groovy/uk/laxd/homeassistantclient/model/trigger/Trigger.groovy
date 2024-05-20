package uk.laxd.homeassistantclient.model.trigger

import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.annotation.JsonSubTypes
import com.fasterxml.jackson.annotation.JsonTypeInfo

@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.EXISTING_PROPERTY,
        property = "triggerType"
)
@JsonSubTypes([
        @JsonSubTypes.Type(value = StateTrigger, name = "state"),
        @JsonSubTypes.Type(value = TimeTrigger, name = "time")
])
abstract class Trigger {

    @JsonProperty("platform")
    abstract TriggerType triggerType()

}
