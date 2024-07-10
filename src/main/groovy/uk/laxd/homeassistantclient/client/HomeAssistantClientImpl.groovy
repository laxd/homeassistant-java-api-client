package uk.laxd.homeassistantclient.client

import jakarta.inject.Inject
import jakarta.inject.Named
import uk.laxd.homeassistantclient.client.exception.InvalidEntityException
import uk.laxd.homeassistantclient.client.exception.NoSuchEntityException
import uk.laxd.homeassistantclient.events.HomeAssistantListener
import uk.laxd.homeassistantclient.model.domain.entity.Entity
import uk.laxd.homeassistantclient.model.domain.entity.EntityFactory
import uk.laxd.homeassistantclient.model.domain.entity.light.LightEntity
import uk.laxd.homeassistantclient.model.domain.response.HomeAssistantPongMessage
import uk.laxd.homeassistantclient.model.domain.service.Service
import uk.laxd.homeassistantclient.model.domain.trigger.Trigger
import uk.laxd.homeassistantclient.model.json.event.Event
import uk.laxd.homeassistantclient.model.json.event.TriggerEvent
import uk.laxd.homeassistantclient.rest.HomeAssistantRestClient
import uk.laxd.homeassistantclient.rest.HomeAssistantRestClientFactory
import uk.laxd.homeassistantclient.ws.HomeAssistantWebSocketClient

import java.util.concurrent.TimeUnit

/**
 * A Home Assistant client implementation that combined REST API and WebSockets
 * to achieve results.
 */
@Named
class HomeAssistantClientImpl implements HomeAssistantClient {

    private static final int TIMEOUT_SECONDS = 10

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

    @Override
    HomeAssistantPongMessage ping() {
        wsClient.ping()
    }

    @Override
    Entity getEntity(String entityId) throws NoSuchEntityException {
        def entity = restClient.getEntity(entityId)

        entityFactory.createEntity(entity)
    }

    @Override
    <E extends Entity> E getEntity(String entityId, Class<E> entityClass)
            throws NoSuchEntityException, InvalidEntityException {
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

    @Override
    void onEvent(String eventType, HomeAssistantListener<Event> listener) {
        wsClient.listenToEvents(eventType, listener).get(TIMEOUT_SECONDS, TimeUnit.SECONDS)
    }

    @Override
    void on(Trigger trigger, HomeAssistantListener<TriggerEvent> listener) {
        wsClient.listenToTrigger(trigger, listener).get(TIMEOUT_SECONDS, TimeUnit.SECONDS)
    }

    @Override
    void on(Collection<Trigger> triggers, HomeAssistantListener<TriggerEvent> listener) {
        wsClient.listenToTriggers(triggers, listener).get(TIMEOUT_SECONDS, TimeUnit.SECONDS)
    }

    @Override
    void turnOn(String entityId) {
        wsClient.turnOn(entityId).get(TIMEOUT_SECONDS, TimeUnit.SECONDS)
    }

    @Override
    void turnOff(String entityId) {
        wsClient.turnOff(entityId).get(TIMEOUT_SECONDS, TimeUnit.SECONDS)
    }

    @Override
    void toggle(String entityId) {
        wsClient.toggle(entityId).get(TIMEOUT_SECONDS, TimeUnit.SECONDS)
    }

    @Override
    void callService(Service service) {
        wsClient.callService(service).get(TIMEOUT_SECONDS, TimeUnit.SECONDS)
    }

}
