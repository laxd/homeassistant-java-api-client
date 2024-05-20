package uk.laxd.homeassistantclient.model.trigger.builder

import uk.laxd.homeassistantclient.model.trigger.StateTrigger

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

    TriggerBuilder duration(Duration duration) {
        this.result.duration = duration
        this
    }
}
