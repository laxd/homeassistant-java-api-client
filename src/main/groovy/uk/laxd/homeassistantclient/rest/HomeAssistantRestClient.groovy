package uk.laxd.homeassistantclient.rest

import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.service.annotation.GetExchange
import uk.laxd.homeassistantclient.model.Entity

interface HomeAssistantRestClient {

    @GetExchange("/")
    String isRunning()

    @GetExchange("/states")
    List<Entity> getAllEntities()

    /**
     * Retrieves a generic entity
     */
    @GetExchange("/states/{id}")
    Entity getEntity(@PathVariable("id") String id)
}