package uk.laxd.homeassistantclient.model.trigger

import java.time.Duration

// TODO: Create sub classes that match each type of trigger in TriggerType?
// TODO: Also merge this with jackson annotated trigger?
class Trigger {

    TriggerType triggerType
    String entity
    String from
    String to
    Duration duration


    @Override
    String toString() {
        "$triggerType $entity" +
                from ? " from '$from'" : "" +
                to ? " to '$to'" : "" +
                duration ? " for '$duration'" : ""
    }
}
