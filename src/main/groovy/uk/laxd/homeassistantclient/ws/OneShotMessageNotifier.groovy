package uk.laxd.homeassistantclient.ws


import groovy.util.logging.Slf4j
import jakarta.inject.Named
import uk.laxd.homeassistantclient.model.json.ws.incoming.ResponseWebSocketMessage

import java.util.concurrent.CompletableFuture
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors
import java.util.concurrent.Future
import java.util.concurrent.TimeoutException

@Named
@Slf4j
class OneShotMessageNotifier {
    private final ExecutorService executor = Executors.newSingleThreadExecutor()
    private final Map<Integer, ResponseWebSocketMessage> messageMap = [:]
    private final List<Integer> expectedIds = []
    private final Object lock = new Object()

    void addMessage(ResponseWebSocketMessage message) {
        log.debug("Adding message $message to notify map and notifying waiting threads")
        synchronized (lock) {
            messageMap.put(message.subscriptionId, message)
            lock.notifyAll()
        }
    }

    boolean isExpecting(Integer id) {
        id in expectedIds
    }

    /**
     * Waits for a message with a given ID to arrive. If the message has already arrived, this
     * method returns instantly and removes the message from the message map.
     * @param id ID of the message to wait for
     * @param timeoutMillis Milliseconds to wait for the message. After this a {@link TimeoutException} is thrown
     * @return The message that was received with this ID, if it was received before the timeout.
     */
    Future<ResponseWebSocketMessage> waitForMessageWithId(Integer id) {
        if (messageMap.containsKey(id)) {
            // The message already arrived
            def message = messageMap.remove(id)
            log.debug("Message with id $id already arrived, returning $message")
            return CompletableFuture.completedFuture(message)
        }

        return setupWaitingThread(id)
    }

    private Future<ResponseWebSocketMessage> setupWaitingThread(int id) {
        log.debug("Waiting for message with ID ${id} - ${messageMap.size()} messages currently stored")
        expectedIds.add(id)

        Future<ResponseWebSocketMessage> future = new CompletableFuture<>()

        executor.submit {
            while (true) {
                log.debug("Checking if message with ID $id is present...")
                if (messageMap.containsKey(id)) {
                    expectedIds.removeElement(id)
                    def message = messageMap.remove(id)
                    log.debug("Found message with ID $id in message map - returning $message")
                    future.complete(message)
                    return
                }

                synchronized (lock) {
                    log.debug("Message with ID $id not present yet, waiting for messages...")
                    lock.wait()
                }
            }
        }

        future
    }
}
