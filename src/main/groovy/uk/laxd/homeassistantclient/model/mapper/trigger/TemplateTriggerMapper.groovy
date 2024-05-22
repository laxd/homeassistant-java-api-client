package uk.laxd.homeassistantclient.model.mapper.trigger

import uk.laxd.homeassistantclient.model.domain.trigger.TemplateTrigger
import uk.laxd.homeassistantclient.model.json.trigger.JsonTrigger

class TemplateTriggerMapper extends TriggerMapper<TemplateTrigger> {
    @Override
    JsonTrigger mapToJson(TemplateTrigger trigger) {
        def jsonTrigger = super.mapToJson(trigger)

        jsonTrigger.addAttribute("value_template", trigger.valueTemplate)

        def f = new ForMapper().mapForToObject(trigger.duration)

        if (f) {
            jsonTrigger.addAttribute("for", f)
        }

        jsonTrigger
    }
}
