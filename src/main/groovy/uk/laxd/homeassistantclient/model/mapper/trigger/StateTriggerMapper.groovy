package uk.laxd.homeassistantclient.model.mapper.trigger

import uk.laxd.homeassistantclient.model.domain.trigger.StateTrigger
import uk.laxd.homeassistantclient.model.json.trigger.JsonTrigger

class StateTriggerMapper extends TriggerMapper<StateTrigger> {

    @Override
    JsonTrigger mapToJson(StateTrigger trigger) {
        def jsonTrigger = super.mapToJson(trigger)

        jsonTrigger.addAttribute("entity_id", trigger.entities)
        jsonTrigger.addAttribute("from", trigger.from)
        jsonTrigger.addAttribute("to", trigger.to)

        def f = new ForMapper().mapForToObject(trigger.duration)

        if (f != null) {
            jsonTrigger.addAttribute("for", f)
        }

        jsonTrigger
    }

}
