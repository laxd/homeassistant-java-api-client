package uk.laxd.homeassistantclient.ws.handler

import groovy.util.logging.Slf4j
import jakarta.inject.Inject
import jakarta.inject.Named
import org.springframework.web.socket.WebSocketSession
import uk.laxd.homeassistantclient.model.json.ws.incoming.ResponseWebSocketMessage
import uk.laxd.homeassistantclient.model.json.ws.incoming.IncomingWebSocketMessage
import uk.laxd.homeassistantclient.ws.OneShotMessageNotifier

@Named
@Slf4j
class OneShotNotifierMessageHandler implements MessageHandler<ResponseWebSocketMessage> {

    private final OneShotMessageNotifier notifier

    @Inject
    OneShotNotifierMessageHandler(OneShotMessageNotifier notifier) {
        this.notifier = notifier
    }

    @Override
    void handle(WebSocketSession session, ResponseWebSocketMessage message) {
        log.info("Adding message")
        notifier.addMessage(message)
    }

    @Override
    boolean canHandle(IncomingWebSocketMessage message) {
        message instanceof ResponseWebSocketMessage && notifier.isExpecting(message.subscriptionId)
    }
}
