package uk.laxd.homeassistantclient.events

import groovy.util.logging.Slf4j
import uk.laxd.homeassistantclient.model.json.event.Event

@Slf4j
class TestListener implements HomeAssistantListener<Event> {

    @Override
    void handle(Event event) {
        log.info("Got event: {}", event)
    }

}
