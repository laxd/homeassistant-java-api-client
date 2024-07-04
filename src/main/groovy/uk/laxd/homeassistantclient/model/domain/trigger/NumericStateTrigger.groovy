package uk.laxd.homeassistantclient.model.domain.trigger

class NumericStateTrigger extends Trigger {

    NumericStateTrigger(String entityId) {
        this.entityId = entityId
    }

    @Override
    TriggerType triggerType() {
        TriggerType.NUMERIC_STATE
    }

    String entityId
    String attribute
    String valueTemplate
    String above
    String below
    For duration

}
