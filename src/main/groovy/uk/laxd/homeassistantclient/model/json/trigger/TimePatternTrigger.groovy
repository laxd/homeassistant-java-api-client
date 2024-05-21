package uk.laxd.homeassistantclient.model.json.trigger

import com.fasterxml.jackson.annotation.JsonInclude

class TimePatternTrigger extends Trigger {

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    String hours

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    String minutes

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    String seconds

    @Override
    TriggerType triggerType() {
        TriggerType.TIME_PATTERN
    }
}
