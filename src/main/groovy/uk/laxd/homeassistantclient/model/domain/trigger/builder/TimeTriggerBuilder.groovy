package uk.laxd.homeassistantclient.model.domain.trigger.builder

import uk.laxd.homeassistantclient.model.domain.trigger.TimeTrigger

class TimeTriggerBuilder extends TriggerBuilder<TimeTrigger> {

    TimeTriggerBuilder andAt(String time) {
        this.result.at += time
        this
    }

}
