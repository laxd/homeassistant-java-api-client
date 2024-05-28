package uk.laxd.homeassistantclient.ws


import jakarta.inject.Inject
import jakarta.inject.Named
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import uk.laxd.homeassistantclient.client.HomeAssistantAuthenticationProvider
import uk.laxd.homeassistantclient.events.HomeAssistantEventListener
import uk.laxd.homeassistantclient.events.HomeAssistantEventListenerRegistry
import uk.laxd.homeassistantclient.model.domain.service.Service
import uk.laxd.homeassistantclient.model.domain.service.ServiceTarget
import uk.laxd.homeassistantclient.model.domain.service.ServiceType
import uk.laxd.homeassistantclient.model.domain.service.TargetType
import uk.laxd.homeassistantclient.model.domain.trigger.Trigger
import uk.laxd.homeassistantclient.model.mapper.service.ServiceMapper
import uk.laxd.homeassistantclient.model.mapper.trigger.TriggerMapperFactory
import uk.laxd.homeassistantclient.ws.message.WebSocketSubscriptionIdPopulator
import uk.laxd.homeassistantclient.ws.message.model.CallServiceWebSocketMessage
import uk.laxd.homeassistantclient.ws.message.model.EventWebSocketMessage
import uk.laxd.homeassistantclient.ws.message.model.JacksonWebSocketMessageConverter
import uk.laxd.homeassistantclient.ws.message.model.PingWebSocketMessage
import uk.laxd.homeassistantclient.ws.message.model.TriggerWebSocketMessage
import uk.laxd.homeassistantclient.ws.session.WebSocketSessionProvider

@Named
class HomeAssistantWebSocketClient {

    private static final Logger logger = LoggerFactory.getLogger(HomeAssistantWebSocketClient)

    private WebSocketSubscriptionIdPopulator idPopulator
    private IdGenerator idGenerator
    private WebSocketSessionProvider webSocketSessionProvider
    private HomeAssistantAuthenticationProvider authenticationProvider
    private HomeAssistantEventListenerRegistry registry
    private JacksonWebSocketMessageConverter webSocketMessageConverter
    private TriggerMapperFactory triggerMapperFactory
    private ServiceMapper serviceMapper

    @Inject
    HomeAssistantWebSocketClient(WebSocketSubscriptionIdPopulator idPopulator,
                                 IdGenerator idGenerator,
                                 WebSocketSessionProvider webSocketSessionProvider,
                                 HomeAssistantEventListenerRegistry registry,
                                 JacksonWebSocketMessageConverter webSocketMessageConverter,
                                 TriggerMapperFactory triggerMapperFactory,
                                 ServiceMapper serviceMapper, HomeAssistantAuthenticationProvider authenticationProvider) {
        this.idPopulator = idPopulator
        this.idGenerator = idGenerator
        this.webSocketSessionProvider = webSocketSessionProvider
        this.registry = registry
        this.webSocketMessageConverter = webSocketMessageConverter
        this.triggerMapperFactory = triggerMapperFactory
        this.serviceMapper = serviceMapper
        this.authenticationProvider = authenticationProvider
    }

    void connect(String url, String token) {
        authenticationProvider.authenticate(url, token)

        // Get the session to ensure it is valid
        webSocketSessionProvider.getOrCreateAuthenticatedSession()
    }

    void listenToEvents(String event, HomeAssistantEventListener listener) {
        logger.info("Subscribing to events of type '{}', listener='{}'", event, listener)

        def message = new EventWebSocketMessage(event)

        // TODO: Mode idPopulator and session to new WebSocketMessageDispatcher class
        // which can also then register the listeners.
        idPopulator.linkMessageToListener(message, listener)
        webSocketSessionProvider.getOrCreateAuthenticatedSession()
                .sendMessage(webSocketMessageConverter.toTextMessage(message))

        registry.register(listener)
    }

    void listenToTrigger(Trigger trigger, HomeAssistantEventListener listener) {
        listenToTriggers([trigger], listener)
    }

    void listenToTriggers(Collection<Trigger> triggers, HomeAssistantEventListener listener) {
        logger.info("Subscribing with triggers [{}], listener='{}'", triggers, listener)

        def jsonTriggers = triggers.collect {
            triggerMapperFactory.getTriggerMapperForTrigger(it).mapToJson(it)
        }

        def message = new TriggerWebSocketMessage(jsonTriggers)

        idPopulator.linkMessageToListener(message, listener)
        webSocketSessionProvider.getOrCreateAuthenticatedSession()
                .sendMessage(webSocketMessageConverter.toTextMessage(message))

        registry.register(listener)
    }

    void turnOn(String entityId) {
        def service = new Service(ServiceType.TURN_ON)
        service.serviceTargets << new ServiceTarget(TargetType.ENTITY, entityId)
        callService(service)
    }

    // TODO: Take in a domain object that wraps all of this?
    void turnOn(String entityId, Map<String, Object> additionalData) {
        def service = new Service(ServiceType.TURN_ON)
        service.serviceTargets << new ServiceTarget(TargetType.ENTITY, entityId)
        service.serviceData = additionalData
        callService(service)
    }

    void turnOff(String entityId) {
        def service = new Service(ServiceType.TURN_OFF)
        service.serviceTargets << new ServiceTarget(TargetType.ENTITY, entityId)
        callService(service)
    }

    void turnOff(String entityId, Map<String, Object> additionalData) {
        def service = new Service(ServiceType.TURN_OFF)
        service.serviceTargets << new ServiceTarget(TargetType.ENTITY, entityId)
        service.serviceData = additionalData
        callService(service)
    }

    void toggle(String entityId, Map<String, Object> additionalData) {
        def service = new Service(ServiceType.TOGGLE)
        service.serviceTargets << new ServiceTarget(TargetType.ENTITY, entityId)
        service.serviceData = additionalData
        callService(service)
    }

    void toggle(String entityId) {
        def service = new Service(ServiceType.TOGGLE)
        service.serviceTargets << new ServiceTarget(TargetType.ENTITY, entityId)
        callService(service)
    }

    void callService(Service service) {
        logger.info("Calling service {}", service)

        def jsonService = serviceMapper.map(service)

        def message = new CallServiceWebSocketMessage(jsonService)
        message.subscriptionId = idGenerator.generateId()

        webSocketSessionProvider.getOrCreateAuthenticatedSession()
                .sendMessage(webSocketMessageConverter.toTextMessage(message))
    }

    void ping() {
        def msg = new PingWebSocketMessage()
        msg.subscriptionId = idGenerator.generateId()
        webSocketSessionProvider.getOrCreateAuthenticatedSession()
                .sendMessage(webSocketMessageConverter.toTextMessage(msg))
    }

}

