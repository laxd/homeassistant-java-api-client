package uk.laxd.homeassistantclient.model.mapper.trigger

import jakarta.inject.Named
import uk.laxd.homeassistantclient.model.domain.trigger.NumericStateTrigger
import uk.laxd.homeassistantclient.model.domain.trigger.StateTrigger
import uk.laxd.homeassistantclient.model.domain.trigger.TemplateTrigger
import uk.laxd.homeassistantclient.model.domain.trigger.TimePatternTrigger
import uk.laxd.homeassistantclient.model.domain.trigger.TimeTrigger
import uk.laxd.homeassistantclient.model.domain.trigger.Trigger

@Named
class TriggerMapperFactory {

    <T extends Trigger> TriggerMapper<T> getTriggerMapperForTrigger(T trigger) {
        switch (trigger) {
            case StateTrigger -> new StateTriggerMapper() as TriggerMapper<T>
            case TimeTrigger -> new TimeTriggerMapper() as TriggerMapper<T>
            case TimePatternTrigger -> new TimePatternTriggerMapper() as TriggerMapper<T>
            case TemplateTrigger -> new TemplateTriggerMapper() as TriggerMapper<T>
            case NumericStateTrigger -> new NumericStateTriggerMapper() as TriggerMapper<T>
            default -> throw new IllegalArgumentException("Mapper for Trigger of type ${trigger.triggerType()} " +
                    "not implemented yet")
        }
    }

}
