package uk.laxd.homeassistantclient.model.json.trigger.builder

class TimeTriggerBuilder extends TriggerBuilder<uk.laxd.homeassistantclient.model.json.trigger.TimeTrigger> {

    TimeTriggerBuilder andAt(String time) {
        this.result.at += time
        this
    }

}
