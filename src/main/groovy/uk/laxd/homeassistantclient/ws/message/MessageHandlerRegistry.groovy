package uk.laxd.homeassistantclient.ws.message

import jakarta.inject.Singleton
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
@Singleton
class MessageHandlerRegistry {

    List<MessageHandler> handlers

    @Autowired
    MessageHandlerRegistry(List<MessageHandler> messageHandlers) {
        this.handlers = []
        this.handlers.addAll(messageHandlers)
    }

    void register(MessageHandler handler) {
        handlers.add(handler)
    }
}