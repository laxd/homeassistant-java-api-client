package uk.laxd.homeassistantclient.ws


import groovy.util.logging.Slf4j
import jakarta.inject.Named
import uk.laxd.homeassistantclient.model.json.ws.incoming.ResponseWebSocketMessage

import java.util.concurrent.TimeoutException

@Named
@Slf4j
class OneShotMessageNotifier {

    private final Map<Integer, ResponseWebSocketMessage> messageMap = [:]
    private final List<Integer> expectedIds = []
    private final Object lock = new Object()

    void addMessage(ResponseWebSocketMessage message) {
        log.debug("Adding message ${message} to notify map and notifying waiting threads")
        synchronized (lock) {
            messageMap.put(message.subscriptionId, message)
            lock.notifyAll()
        }
    }

    boolean isExpecting(Integer id) {
        id in expectedIds
    }

    ResponseWebSocketMessage waitForMessageWithId(Integer id, long timeoutMillis) {
        expectedIds.add(id)
        def expiryTime = System.currentTimeMillis() + timeoutMillis
        while(true) {
            log.info("Waiting for message with ID ${id} - ${messageMap.size()} messages currently stored")
            if (messageMap.containsKey(id)) {
                log.info("Found message with ID ${id} in message map!")
                expectedIds.removeElement(id)
                return messageMap.remove(id)
            } else if (System.currentTimeMillis() > expiryTime) {
                expectedIds.remove(id)
                throw new TimeoutException("Waiting for message with ID ${id}, but not received within ${timeoutMillis}ms")
            } else {
                synchronized (lock) {
                    log.info("Starting wait ")
                    lock.wait(timeoutMillis)
                }
            }
        }
    }

}
