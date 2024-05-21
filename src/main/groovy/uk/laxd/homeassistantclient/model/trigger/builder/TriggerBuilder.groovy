package uk.laxd.homeassistantclient.model.trigger.builder

import uk.laxd.homeassistantclient.model.trigger.StateTrigger
import uk.laxd.homeassistantclient.model.trigger.TemplateTrigger
import uk.laxd.homeassistantclient.model.trigger.TimePatternTrigger
import uk.laxd.homeassistantclient.model.trigger.TimeTrigger
import uk.laxd.homeassistantclient.model.trigger.Trigger

class TriggerBuilder<T extends Trigger> {

    T result

    protected TriggerBuilder() {}

    static StateTriggerBuilder onStateChange(String entityId, String... additionalEntityIds) {
        def builder = new StateTriggerBuilder()
        builder.result = new StateTrigger(entityId, additionalEntityIds)
        builder
    }

    static TimeTriggerBuilder dailyAt(String time, String... additionalTime) {
        def builder = new TimeTriggerBuilder()
        builder.result = new TimeTrigger(time)
        builder.result.at += Arrays.asList(additionalTime)
        builder
    }

    static TimePatternTriggerBuilder timePattern() {
        def builder = new TimePatternTriggerBuilder()
        builder.result = new TimePatternTrigger()
        builder
    }

    static TemplateTriggerBuilder valueTemplate(String template) {
        def builder = new TemplateTriggerBuilder()
        builder.result = new TemplateTrigger(template)
        builder
    }

    Trigger build() {
        this.result
    }
}
