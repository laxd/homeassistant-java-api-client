package uk.laxd.homeassistantclient.model.mapper.trigger

import jakarta.inject.Named
import uk.laxd.homeassistantclient.model.domain.trigger.Trigger
import uk.laxd.homeassistantclient.model.json.trigger.JsonTrigger

@Named
class TriggerMapper<T extends Trigger> {

    JsonTrigger mapToJson(T trigger) {
        def jsonTrigger = new JsonTrigger()

        jsonTrigger.type = trigger.triggerType().string

        jsonTrigger
    }

}
