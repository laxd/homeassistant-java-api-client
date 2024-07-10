package uk.laxd.homeassistantclient.model.domain.trigger.builder

import uk.laxd.homeassistantclient.model.domain.trigger.For
import uk.laxd.homeassistantclient.model.domain.trigger.StateTrigger

import java.time.Duration

class StateTriggerBuilder extends TriggerBuilder<StateTrigger> {

    TriggerBuilder from(String from) {
        this.result.from = from
        this
    }

    TriggerBuilder to(String to) {
        this.result.to = to
        this
    }

    // TODO: Also allow taking in a h/m/s duration including templates
    TriggerBuilder duration(Duration duration) {
        this.result.duration = new For(duration)
        this
    }

}
