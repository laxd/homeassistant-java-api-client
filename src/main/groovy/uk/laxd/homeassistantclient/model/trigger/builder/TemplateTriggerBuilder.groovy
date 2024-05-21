package uk.laxd.homeassistantclient.model.trigger.builder

import uk.laxd.homeassistantclient.model.trigger.For
import uk.laxd.homeassistantclient.model.trigger.TemplateTrigger

import java.time.Duration

class TemplateTriggerBuilder extends TriggerBuilder<TemplateTrigger> {

    TriggerBuilder duration(Duration duration) {
        this.result.duration = new For(duration)
        this
    }

}
