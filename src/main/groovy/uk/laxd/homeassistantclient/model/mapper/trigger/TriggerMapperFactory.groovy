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
        return switch (trigger) {
            case StateTrigger -> new StateTriggerMapper()
            case TimeTrigger -> new TimeTriggerMapper()
            case TimePatternTrigger -> new TimePatternTriggerMapper()
            case TemplateTrigger -> new TemplateTriggerMapper()
            case NumericStateTrigger -> new NumericStateTriggerMapper()
            default -> throw new IllegalArgumentException("Mapper for Trigger of type ${trigger.triggerType()} not implemented yet")
        } as TriggerMapper<T>
    }

}
