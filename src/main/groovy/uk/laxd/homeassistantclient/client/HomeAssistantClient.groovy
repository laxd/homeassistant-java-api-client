package uk.laxd.homeassistantclient.client

import jakarta.inject.Inject
import org.springframework.context.annotation.AnnotationConfigApplicationContext
import uk.laxd.homeassistantclient.events.HomeAssistantEventListener
import uk.laxd.homeassistantclient.model.Entity
import uk.laxd.homeassistantclient.model.trigger.Trigger
import uk.laxd.homeassistantclient.model.event.Event
import uk.laxd.homeassistantclient.model.event.TriggerEvent
import uk.laxd.homeassistantclient.rest.HomeAssistantRestClient
import uk.laxd.homeassistantclient.spring.HomeAssistantClientConfiguration
import uk.laxd.homeassistantclient.ws.HomeAssistantWebSocketClient

class HomeAssistantClient {

    private HomeAssistantRestClient restClient
    private HomeAssistantWebSocketClient wsClient

    @Inject
    HomeAssistantClient(HomeAssistantRestClient restClient, HomeAssistantWebSocketClient wsClient) {
        this.restClient = restClient
        this.wsClient = wsClient
    }

    void ping() {
        wsClient.ping()
    }

    Entity getEntity(String entityId) {
        restClient.getEntity(entityId)
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

    /**
     * Create a new client for home assistant.
     *
     * The url should be a URL that points to the base of the home assistant instance, don't
     * include any paths e.g. /api or /lovelace
     *
     * The token should be a <a href="https://developers.home-assistant.io/docs/auth_api/#long-lived-access-token">
     * Long-Lived access token</a>.
     */
    static HomeAssistantClient createClient(String url, String token) {
        def context = new AnnotationConfigApplicationContext(HomeAssistantClientConfiguration.class)

        def auth = context.getBean(HomeAssistantAuthentication.class)
        auth.url = url
        auth.token = token

        def wsClient = context.getBean(HomeAssistantWebSocketClient.class)
        def restClient = context.getBean(HomeAssistantRestClient.class)

        return new HomeAssistantClient(restClient, wsClient)
    }

}