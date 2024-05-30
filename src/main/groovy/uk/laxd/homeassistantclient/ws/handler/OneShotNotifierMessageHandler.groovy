package uk.laxd.homeassistantclient.ws.handler

import groovy.util.logging.Slf4j
import jakarta.inject.Inject
import jakarta.inject.Named
import org.springframework.web.socket.WebSocketSession
import uk.laxd.homeassistantclient.model.json.ws.HomeAssistantResponseMessage
import uk.laxd.homeassistantclient.model.json.ws.HomeAssistantWebSocketMessage
import uk.laxd.homeassistantclient.ws.OneShotMessageNotifier

@Named
@Slf4j
class OneShotNotifierMessageHandler implements MessageHandler<HomeAssistantResponseMessage> {

    private final OneShotMessageNotifier notifier

    @Inject
    OneShotNotifierMessageHandler(OneShotMessageNotifier notifier) {
        this.notifier = notifier
    }

    @Override
    void handle(WebSocketSession session, HomeAssistantResponseMessage message) {
        log.info("Adding message")
        notifier.addMessage(message)
    }

    @Override
    boolean canHandle(HomeAssistantWebSocketMessage message) {
        def canHandle = message instanceof HomeAssistantResponseMessage && notifier.isExpecting(message.subscriptionId)

        log.info("Notifiable ${message.unknown}? ${canHandle}")

        canHandle

    }
}
