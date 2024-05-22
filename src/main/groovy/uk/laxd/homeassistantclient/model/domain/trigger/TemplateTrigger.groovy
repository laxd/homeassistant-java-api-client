package uk.laxd.homeassistantclient.model.domain.trigger

class TemplateTrigger extends Trigger {

    TemplateTrigger(String valueTemplate) {
        this.valueTemplate = valueTemplate
    }

    @Override
    TriggerType triggerType() {
        TriggerType.TEMPLATE
    }

    String valueTemplate
    For duration

}
