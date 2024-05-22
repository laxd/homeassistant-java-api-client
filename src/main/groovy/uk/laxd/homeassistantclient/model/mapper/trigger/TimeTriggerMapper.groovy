package uk.laxd.homeassistantclient.model.mapper.trigger

import uk.laxd.homeassistantclient.model.domain.trigger.TimeTrigger
import uk.laxd.homeassistantclient.model.json.trigger.JsonTrigger

class TimeTriggerMapper extends TriggerMapper<TimeTrigger> {
    @Override
    JsonTrigger mapToJson(TimeTrigger trigger) {
        def jsonTrigger = super.mapToJson(trigger)

        jsonTrigger.addAttribute("at", trigger.at)

        jsonTrigger
    }
}
