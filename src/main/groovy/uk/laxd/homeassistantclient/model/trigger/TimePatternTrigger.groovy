package uk.laxd.homeassistantclient.model.trigger

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonValue

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
