package uk.laxd.homeassistantclient.model.domain.trigger

import groovy.transform.ToString

@ToString(includeNames = true, includes = "entities,from,to")
class StateTrigger extends Trigger {

    StateTrigger(String entity, String... additionalEntities) {
        this.entities = [entity] + Arrays.asList(additionalEntities)
    }

    Collection<String> entities
    String from
    String to
    For duration

    @Override
    TriggerType triggerType() {
        TriggerType.STATE
    }

}
