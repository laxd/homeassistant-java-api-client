package uk.laxd.homeassistantclient.model.domain.trigger

class TimePatternTrigger extends Trigger {

    String hours
    String minutes
    String seconds

    @Override
    TriggerType triggerType() {
        TriggerType.TIME_PATTERN
    }
}
