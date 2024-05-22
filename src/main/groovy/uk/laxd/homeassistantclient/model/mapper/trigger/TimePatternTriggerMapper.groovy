package uk.laxd.homeassistantclient.model.mapper.trigger

import uk.laxd.homeassistantclient.model.domain.trigger.TimePatternTrigger
import uk.laxd.homeassistantclient.model.json.trigger.JsonTrigger

class TimePatternTriggerMapper extends TriggerMapper<TimePatternTrigger> {
    @Override
    JsonTrigger mapToJson(TimePatternTrigger trigger) {
        def jsonTrigger = super.mapToJson(trigger)

        jsonTrigger.addAttribute("hours", trigger.hours)
        jsonTrigger.addAttribute("minutes", trigger.minutes)
        jsonTrigger.addAttribute("seconds", trigger.seconds)

        jsonTrigger
    }
}
