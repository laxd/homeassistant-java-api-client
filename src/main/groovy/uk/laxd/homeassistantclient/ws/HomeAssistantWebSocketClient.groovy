package uk.laxd.homeassistantclient.ws


import jakarta.inject.Inject
import jakarta.inject.Named
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import uk.laxd.homeassistantclient.client.HomeAssistantAuthenticationProvider
import uk.laxd.homeassistantclient.events.HomeAssistantEventListener
import uk.laxd.homeassistantclient.events.HomeAssistantEventListenerRegistry
import uk.laxd.homeassistantclient.model.domain.response.HomeAssistantPongMessage
import uk.laxd.homeassistantclient.model.domain.service.Service
import uk.laxd.homeassistantclient.model.domain.service.ServiceTarget
import uk.laxd.homeassistantclient.model.domain.service.ServiceType
import uk.laxd.homeassistantclient.model.domain.service.TargetType
import uk.laxd.homeassistantclient.model.domain.trigger.Trigger
import uk.laxd.homeassistantclient.model.mapper.WebSocketMessageMapper
import uk.laxd.homeassistantclient.model.mapper.service.ServiceMapper
import uk.laxd.homeassistantclient.model.mapper.trigger.TriggerMapperFactory

import uk.laxd.homeassistantclient.model.json.ws.outgoing.CallServiceWebSocketMessage
import uk.laxd.homeassistantclient.model.json.ws.outgoing.EventSubscriptionWebSocketMessage
import uk.laxd.homeassistantclient.ws.message.model.JacksonWebSocketMessageConverter
import uk.laxd.homeassistantclient.model.json.ws.outgoing.PingWebSocketMessage
import uk.laxd.homeassistantclient.model.json.ws.outgoing.TriggerWebSocketMessage
import uk.laxd.homeassistantclient.ws.session.WebSocketSessionProvider

import java.time.Duration

@Named
class HomeAssistantWebSocketClient {

    private static final Logger logger = LoggerFactory.getLogger(HomeAssistantWebSocketClient)

    private WebSocketSessionProvider webSocketSessionProvider
    private HomeAssistantAuthenticationProvider authenticationProvider
    private HomeAssistantEventListenerRegistry registry
    private JacksonWebSocketMessageConverter webSocketMessageConverter
    private TriggerMapperFactory triggerMapperFactory
    private ServiceMapper serviceMapper
    private HomeAssistantWebSocketMessageDispatcher messageDispatcher
    private WebSocketMessageMapper messageMapper

    @Inject
    HomeAssistantWebSocketClient(WebSocketSessionProvider webSocketSessionProvider,
                                 HomeAssistantEventListenerRegistry registry,
                                 JacksonWebSocketMessageConverter webSocketMessageConverter,
                                 TriggerMapperFactory triggerMapperFactory,
                                 ServiceMapper serviceMapper,
                                 HomeAssistantAuthenticationProvider authenticationProvider,
                                 HomeAssistantWebSocketMessageDispatcher messageDispatcher,
                                 WebSocketMessageMapper messageMapper) {
        this.webSocketSessionProvider = webSocketSessionProvider
        this.registry = registry
        this.webSocketMessageConverter = webSocketMessageConverter
        this.triggerMapperFactory = triggerMapperFactory
        this.serviceMapper = serviceMapper
        this.authenticationProvider = authenticationProvider
        this.messageDispatcher = messageDispatcher
        this.messageMapper = messageMapper
    }

    void connect(String url, String token) {
        authenticationProvider.authenticate(url, token)

        // Get the session to ensure it is valid
        webSocketSessionProvider.getOrCreateAuthenticatedSession()
    }

    void listenToEvents(String event, HomeAssistantEventListener listener) {
        logger.info("Subscribing to events of type '{}', listener='{}'", event, listener)

        def message = new EventSubscriptionWebSocketMessage(event)
        messageDispatcher.sendMessageWithResponseListener(message, listener)
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

        messageDispatcher.sendMessageWithResponseListener(message, listener)
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

        messageDispatcher.sendSingleMessage(message)
    }

    HomeAssistantPongMessage ping() {
        def message = new PingWebSocketMessage()
        def response = messageDispatcher.sendMessageAndWaitForResponse(message, Duration.ofSeconds(10))

        messageMapper.map(response)
    }

}

