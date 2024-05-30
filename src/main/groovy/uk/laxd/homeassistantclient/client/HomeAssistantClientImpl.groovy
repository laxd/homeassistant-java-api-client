package uk.laxd.homeassistantclient.client

import jakarta.inject.Inject
import uk.laxd.homeassistantclient.events.HomeAssistantEventListener
import uk.laxd.homeassistantclient.model.domain.entity.Entity
import uk.laxd.homeassistantclient.model.domain.entity.EntityImpl
import uk.laxd.homeassistantclient.model.domain.trigger.Trigger
import uk.laxd.homeassistantclient.model.json.event.Event
import uk.laxd.homeassistantclient.model.json.event.TriggerEvent
import uk.laxd.homeassistantclient.model.json.ws.HomeAssistantResponseMessage
import uk.laxd.homeassistantclient.rest.HomeAssistantRestClient
import uk.laxd.homeassistantclient.ws.HomeAssistantWebSocketClient

/**
 * A Home Assistant client implementation that combined REST API and WebSockets
 * to achieve results.
 */
class HomeAssistantClientImpl implements HomeAssistantClient {

    private HomeAssistantRestClient restClient
    private HomeAssistantWebSocketClient wsClient

    @Inject
    HomeAssistantClientImpl(HomeAssistantRestClient restClient, HomeAssistantWebSocketClient wsClient) {
        this.restClient = restClient
        this.wsClient = wsClient
    }

    HomeAssistantResponseMessage ping() {
        wsClient.ping()
    }

    Entity getEntity(String entityId) {
        def entity = restClient.getEntity(entityId)
        return new EntityImpl(wsClient, entity)
    }

    void onEvent(String eventType, HomeAssistantEventListener<Event> listener) {
        wsClient.listenToEvents(eventType, listener)
    }

    void on(Trigger trigger, HomeAssistantEventListener<TriggerEvent> listener) {
        wsClient.listenToTrigger(trigger, listener)
    }

    void on(Collection<Trigger> triggers, HomeAssistantEventListener<TriggerEvent> listener) {
        wsClient.listenToTriggers(triggers, listener)
    }

    void turnOn(String entityId) {
        wsClient.turnOn(entityId)
    }

    void turnOff(String entityId) {
        wsClient.turnOff(entityId)
    }

    void toggle(String entityId) {
        wsClient.toggle(entityId)
    }

}