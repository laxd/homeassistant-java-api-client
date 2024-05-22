package uk.laxd.homeassistantclient.model.domain.trigger

import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.annotation.JsonSubTypes
import com.fasterxml.jackson.annotation.JsonTypeInfo

abstract class Trigger {

    abstract TriggerType triggerType()

}
