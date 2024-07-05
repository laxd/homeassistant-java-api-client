package uk.laxd.homeassistantclient.ws


import jakarta.inject.Inject
import jakarta.inject.Named
import org.slf4j.Logger
import org.slf4j.LoggerFactory

import uk.laxd.homeassistantclient.events.HomeAssistantEventListenerRegistry
import uk.laxd.homeassistantclient.events.HomeAssistantListener
import uk.laxd.homeassistantclient.model.domain.response.HomeAssistantPongMessage
import uk.laxd.homeassistantclient.model.domain.service.Service
import uk.laxd.homeassistantclient.model.domain.service.ServiceTarget
import uk.laxd.homeassistantclient.model.domain.service.ServiceType
import uk.laxd.homeassistantclient.model.domain.service.TargetType
import uk.laxd.homeassistantclient.model.domain.trigger.Trigger
import uk.laxd.homeassistantclient.model.json.ws.incoming.PongWebSocketMessage
import uk.laxd.homeassistantclient.model.json.ws.incoming.ResultWebSocketMessage
import uk.laxd.homeassistantclient.model.mapper.WebSocketMessageMapper
import uk.laxd.homeassistantclient.model.mapper.service.ServiceMapper
import uk.laxd.homeassistantclient.model.mapper.trigger.TriggerMapperFactory

import uk.laxd.homeassistantclient.model.json.ws.outgoing.CallServiceWebSocketMessage
import uk.laxd.homeassistantclient.model.json.ws.outgoing.EventSubscriptionWebSocketMessage
import uk.laxd.homeassistantclient.ws.message.model.JacksonWebSocketMessageConverter
import uk.laxd.homeassistantclient.model.json.ws.outgoing.PingWebSocketMessage
import uk.laxd.homeassistantclient.model.json.ws.outgoing.TriggerWebSocketMessage
import uk.laxd.homeassistantclient.ws.session.WebSocketSessionProvider

import java.util.concurrent.Future
import java.util.concurrent.TimeUnit

@Named
class HomeAssistantWebSocketClient {

    private static final Logger logger = LoggerFactory.getLogger(HomeAssistantWebSocketClient)

    private WebSocketSessionProvider webSocketSessionProvider
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
                                 HomeAssistantWebSocketMessageDispatcher messageDispatcher,
                                 WebSocketMessageMapper messageMapper) {
        this.webSocketSessionProvider = webSocketSessionProvider
        this.registry = registry
        this.webSocketMessageConverter = webSocketMessageConverter
        this.triggerMapperFactory = triggerMapperFactory
        this.serviceMapper = serviceMapper
        this.messageDispatcher = messageDispatcher
        this.messageMapper = messageMapper
    }

    void connect(String url, String token) {
        webSocketSessionProvider.authenticate(url, token)
    }

    Future<ResultWebSocketMessage> listenToEvents(String event, HomeAssistantListener listener) {
        logger.info("Subscribing to events of type '{}', listener='{}'", event, listener)

        def message = new EventSubscriptionWebSocketMessage(event)
        messageDispatcher.sendMessageWithListener(message, listener)
    }

    Future<ResultWebSocketMessage> listenToTrigger(Trigger trigger, HomeAssistantListener listener) {
        listenToTriggers([trigger], listener)
    }

    Future<ResultWebSocketMessage> listenToTriggers(Collection<Trigger> triggers, HomeAssistantListener listener) {
        logger.info("Subscribing with triggers [{}], listener='{}'", triggers, listener)

        def jsonTriggers = triggers.collect {
            triggerMapperFactory.getTriggerMapperForTrigger(it).mapToJson(it)
        }

        def message = new TriggerWebSocketMessage(jsonTriggers)

        messageDispatcher.sendMessageWithListener(message, listener)
    }

    Future<ResultWebSocketMessage> turnOn(String entityId) {
        def service = new Service(ServiceType.TURN_ON)
        service.targets << new ServiceTarget(TargetType.ENTITY, entityId)
        callService(service)
    }

    // TODO: Take in a domain object that wraps all of this?
    Future<ResultWebSocketMessage> turnOn(String entityId, Map<String, Object> additionalData) {
        def service = new Service(ServiceType.TURN_ON)
        service.targets << new ServiceTarget(TargetType.ENTITY, entityId)
        service.data = additionalData
        callService(service)
    }

    Future<ResultWebSocketMessage> turnOff(String entityId) {
        def service = new Service(ServiceType.TURN_OFF)
        service.targets << new ServiceTarget(TargetType.ENTITY, entityId)
        callService(service)
    }

    Future<ResultWebSocketMessage> turnOff(String entityId, Map<String, Object> additionalData) {
        def service = new Service(ServiceType.TURN_OFF)
        service.targets << new ServiceTarget(TargetType.ENTITY, entityId)
        service.data = additionalData
        callService(service)
    }

    Future<ResultWebSocketMessage> toggle(String entityId, Map<String, Object> additionalData) {
        def service = new Service(ServiceType.TOGGLE)
        service.targets << new ServiceTarget(TargetType.ENTITY, entityId)
        service.data = additionalData
        callService(service)
    }

    Future<ResultWebSocketMessage> toggle(String entityId) {
        def service = new Service(ServiceType.TOGGLE)
        service.targets << new ServiceTarget(TargetType.ENTITY, entityId)
        callService(service)
    }

    Future<ResultWebSocketMessage> callService(Service service) {
        logger.info("Calling service {}", service)

        def jsonService = serviceMapper.map(service)

        def message = new CallServiceWebSocketMessage(jsonService)

        messageDispatcher.sendMessage(message, ResultWebSocketMessage)
    }

    HomeAssistantPongMessage ping() {
        def message = new PingWebSocketMessage()
        def response = messageDispatcher.sendMessage(message, PongWebSocketMessage)
        def b = response.get(10, TimeUnit.SECONDS)
        messageMapper.map(b)
    }

}

