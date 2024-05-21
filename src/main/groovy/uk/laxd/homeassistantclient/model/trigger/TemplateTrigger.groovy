package uk.laxd.homeassistantclient.model.trigger

import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.annotation.JsonUnwrapped

class TemplateTrigger extends Trigger {

    TemplateTrigger(String valueTemplate) {
        this.valueTemplate = valueTemplate
    }

    @Override
    TriggerType triggerType() {
        TriggerType.TEMPLATE
    }

    @JsonProperty("value_template")
    String valueTemplate

    @JsonProperty("for")
    @JsonUnwrapped
    For duration

}
