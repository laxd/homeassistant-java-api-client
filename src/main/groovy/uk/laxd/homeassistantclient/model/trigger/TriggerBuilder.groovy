package uk.laxd.homeassistantclient.model.trigger

import java.time.Duration

class TriggerBuilder {

    Trigger result = new Trigger()

    private TriggerBuilder() {
    }

    static TriggerBuilder onStateChange(String entityId) {
        def builder = new TriggerBuilder()
        builder.result.entity = entityId
        builder.result.triggerType = TriggerType.STATE
        builder
    }

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

    Trigger build() {
        this.result
    }
}
