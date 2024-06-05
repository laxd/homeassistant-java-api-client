package uk.laxd.homeassistantclient.client

import jakarta.inject.Inject
import jakarta.inject.Named
import uk.laxd.homeassistantclient.client.exception.InvalidEntityException
import uk.laxd.homeassistantclient.client.exception.NoSuchEntityException
import uk.laxd.homeassistantclient.events.HomeAssistantEventListener
import uk.laxd.homeassistantclient.model.domain.entity.Entity
import uk.laxd.homeassistantclient.model.domain.entity.EntityFactory
import uk.laxd.homeassistantclient.model.domain.entity.light.LightEntity
import uk.laxd.homeassistantclient.model.domain.response.HomeAssistantPongMessage
import uk.laxd.homeassistantclient.model.domain.trigger.Trigger
import uk.laxd.homeassistantclient.model.json.event.Event
import uk.laxd.homeassistantclient.model.json.event.TriggerEvent
import uk.laxd.homeassistantclient.rest.HomeAssistantRestClient
import uk.laxd.homeassistantclient.rest.HomeAssistantRestClientFactory
import uk.laxd.homeassistantclient.ws.HomeAssistantWebSocketClient

/**
 * A Home Assistant client implementation that combined REST API and WebSockets
 * to achieve results.
 */
@Named
class HomeAssistantClientImpl implements HomeAssistantClient {

    private final HomeAssistantRestClientFactory restClientFactory

    private HomeAssistantRestClient restClient
    private final HomeAssistantWebSocketClient wsClient
    private final EntityFactory entityFactory

    @Inject
    HomeAssistantClientImpl(HomeAssistantRestClientFactory restClientFactory,
                            HomeAssistantWebSocketClient wsClient,
                            EntityFactory entityFactory) {
        this.restClientFactory = restClientFactory
        this.wsClient = wsClient
        this.entityFactory = entityFactory
    }

    @Override
    void connect(String url, String token) {
        this.restClient = restClientFactory.createRestClient(url, token)
        this.wsClient.connect(url, token)
    }

    HomeAssistantPongMessage ping() {
        wsClient.ping()
    }

    Entity getEntity(String entityId) throws NoSuchEntityException {
        def entity = restClient.getEntity(entityId)

        entityFactory.createEntity(entity)
    }

    @Override
    <E extends Entity> E getEntity(String entityId, Class<E> entityClass) throws NoSuchEntityException, InvalidEntityException {
        def entity = restClient.getEntity(entityId)

        def e = entityFactory.createEntity(entity)

        if (e.class !== entityClass) {
            throw new InvalidEntityException("Entity ${e} does not match expected type ${entityClass}")
        }

        e as E
    }

    LightEntity getLightEntity(String entityId) throws NoSuchEntityException {
        def entity = restClient.getEntity(entityId)

        entityFactory.createLightEntity(entity)
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