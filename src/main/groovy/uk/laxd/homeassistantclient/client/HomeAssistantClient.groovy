package uk.laxd.homeassistantclient.client

import uk.laxd.homeassistantclient.events.HomeAssistantEventListener
import uk.laxd.homeassistantclient.model.domain.entity.Entity
import uk.laxd.homeassistantclient.model.domain.trigger.Trigger
import uk.laxd.homeassistantclient.model.json.event.Event
import uk.laxd.homeassistantclient.model.json.event.TriggerEvent

// TODO: Add some documentation
interface HomeAssistantClient {

    void ping()
    Entity getEntity(String entityId)
    void onEvent(String eventType, HomeAssistantEventListener<Event> listener)
    void on(Trigger trigger, HomeAssistantEventListener<TriggerEvent> listener)
    void on(Collection<Trigger> triggers, HomeAssistantEventListener<TriggerEvent> listener)
    void turnOn(String entityId)
    void turnOff(String entityId)
    void toggle(String entityId)

}
