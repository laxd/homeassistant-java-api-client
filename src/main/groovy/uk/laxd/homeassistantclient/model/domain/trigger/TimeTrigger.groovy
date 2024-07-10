package uk.laxd.homeassistantclient.model.domain.trigger

/**
 * <p>
 *     Trigger at a given time.
 * </p>
 *
 * <p>
 *     {@code at} can be one of three formats:
 * </p>
 * <ol>
 *   <li>A time string e.g. 12:00:00 - Seconds can be omitted and will be assumed to be "00"</li>
 *   <li>The entity ID of an input datetime, e.g. "input_datetime.childrens_bedtime"</li>
 *   <li>The entity ID of a device with a "timestamp" device class e.g. "sensor.phone_next_alarm"</li>
 * </ol>
 * <p>
 *     If multiple items are provided, the trigger runs at a time that matches any of the items.
 * </p>
 */
class TimeTrigger extends Trigger {

    TimeTrigger(String at) {
        this.at = [at]
    }

    Collection<String> at

    @Override
    TriggerType triggerType() {
        TriggerType.TIME
    }

}
