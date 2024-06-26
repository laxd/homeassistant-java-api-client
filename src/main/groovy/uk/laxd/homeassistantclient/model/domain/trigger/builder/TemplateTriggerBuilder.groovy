package uk.laxd.homeassistantclient.model.domain.trigger.builder

import uk.laxd.homeassistantclient.model.domain.trigger.For
import uk.laxd.homeassistantclient.model.domain.trigger.TemplateTrigger

import java.time.Duration

class TemplateTriggerBuilder extends TriggerBuilder<TemplateTrigger> {

    TriggerBuilder duration(Duration duration) {
        this.result.duration = new For(duration)
        this
    }

}
