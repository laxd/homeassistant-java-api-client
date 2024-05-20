package uk.laxd.homeassistantclient.model.trigger.builder

import uk.laxd.homeassistantclient.model.trigger.TimeTrigger

class TimeTriggerBuilder extends TriggerBuilder<TimeTrigger> {

    TimeTriggerBuilder andAt(String time) {
        this.result.at += time
        this
    }

}
