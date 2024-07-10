package uk.laxd.homeassistantclient.model.mapper.trigger

import uk.laxd.homeassistantclient.model.domain.trigger.NumericStateTrigger
import uk.laxd.homeassistantclient.model.json.trigger.JsonTrigger

class NumericStateTriggerMapper extends TriggerMapper<NumericStateTrigger> {

    @Override
    JsonTrigger mapToJson(NumericStateTrigger trigger) {
        def jsonTrigger = super.mapToJson(trigger)

        jsonTrigger.addAttribute("entity_id", trigger.entityId)
        jsonTrigger.addAttribute("attribute", trigger.attribute)
        jsonTrigger.addAttribute("value_template", trigger.valueTemplate)
        jsonTrigger.addAttribute("above", trigger.above)
        jsonTrigger.addAttribute("below", trigger.below)

        def f = new ForMapper().mapForToObject(trigger.duration)

        if (f != null) {
            jsonTrigger.addAttribute("for", f)
        }

        jsonTrigger
    }

}
