package uk.laxd.homeassistantclient.events

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import uk.laxd.homeassistantclient.model.event.Event

class TestListener extends HomeAssistantEventListener<Event> {
    private static final Logger logger = LoggerFactory.getLogger(TestListener.class)

    void handleMessage(Event event) {
        logger.info("Got event: {}", event.data)
    }
}
