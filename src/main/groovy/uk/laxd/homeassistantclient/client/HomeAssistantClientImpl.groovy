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
import uk.laxd.homeassistantclient.timeout.TimeoutService
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
    private final TimeoutService timeoutService

    @Inject
    HomeAssistantClientImpl(HomeAssistantRestClientFactory restClientFactory,
                            HomeAssistantWebSocketClient wsClient,
                            EntityFactory entityFactory,
                            TimeoutService timeoutService) {
        this.restClientFactory = restClientFactory
        this.wsClient = wsClient
        this.entityFactory = entityFactory
        this.timeoutService = timeoutService
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

        entityFactory.createEntity(this, entity)
    }

    @Override
    <E extends Entity> E getEntity(String entityId, Class<E> entityClass)
            throws NoSuchEntityException, InvalidEntityException {
        def entity = restClient.getEntity(entityId)

        def e = entityFactory.createEntity(this, entity)

        if (e.class !== entityClass) {
            throw new InvalidEntityException("Entity ${e} does not match expected type ${entityClass}")
        }

        e as E
    }

    LightEntity getLightEntity(String entityId) throws NoSuchEntityException {
        def entity = restClient.getEntity(entityId)

        entityFactory.createLightEntity(this, entity)
    }

    @Override
    void onEvent(String eventType, HomeAssistantListener<Event> listener) {
        def future = wsClient.listenToEvents(eventType, listener)

        timeoutService.resolveWithinTimeout(future)
    }

    @Override
    void on(Trigger trigger, HomeAssistantListener<TriggerEvent> listener) {
        def future = wsClient.listenToTrigger(trigger, listener)

        timeoutService.resolveWithinTimeout(future)
    }

    @Override
    void on(Collection<Trigger> triggers, HomeAssistantListener<TriggerEvent> listener) {
        def future = wsClient.listenToTriggers(triggers, listener)

        timeoutService.resolveWithinTimeout(future)
    }

    @Override
    void turnOn(String entityId, Map<String, Object> attributes = [:]) {
        def future = wsClient.turnOn(entityId, attributes)

        timeoutService.resolveWithinTimeout(future)
    }

    @Override
    void turnOff(String entityId, Map<String, Object> attributes = [:]) {
        def future = wsClient.turnOff(entityId, attributes)

        timeoutService.resolveWithinTimeout(future)
    }

    @Override
    void toggle(String entityId, Map<String, Object> attributes = [:]) {
        def future = wsClient.toggle(entityId, attributes)

        timeoutService.resolveWithinTimeout(future)
    }

    @Override
    void callService(Service service) {
        def future = wsClient.callService(service)

        timeoutService.resolveWithinTimeout(future)
    }

}
