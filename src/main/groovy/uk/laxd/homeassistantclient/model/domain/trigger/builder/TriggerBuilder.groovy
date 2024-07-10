package uk.laxd.homeassistantclient.model.domain.trigger.builder

import uk.laxd.homeassistantclient.model.domain.trigger.NumericStateTrigger
import uk.laxd.homeassistantclient.model.domain.trigger.StateTrigger
import uk.laxd.homeassistantclient.model.domain.trigger.TemplateTrigger
import uk.laxd.homeassistantclient.model.domain.trigger.TimePatternTrigger
import uk.laxd.homeassistantclient.model.domain.trigger.TimeTrigger
import uk.laxd.homeassistantclient.model.domain.trigger.Trigger

class TriggerBuilder<T extends Trigger> {

    T result

    protected TriggerBuilder() { }

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

    static NumericStateTriggerBuilder onNumericStateChange(String entityId) {
        def builder = new NumericStateTriggerBuilder()
        builder.result = new NumericStateTrigger(entityId)
        builder
    }

    static NumericStateTriggerBuilder onNumericAttributeChange(String entityId, String attribute) {
        def builder = new NumericStateTriggerBuilder()
        builder.result = new NumericStateTrigger(entityId)
        builder.result.attribute = attribute
        builder
    }

    static NumericStateTriggerBuilder onNumericStateChangeTemplate(String entityId, String valueTemplate) {
        def builder = new NumericStateTriggerBuilder()
        builder.result = new NumericStateTrigger(entityId)
        builder.result.attribute = valueTemplate
        builder
    }

    Trigger build() {
        this.result
    }

}

