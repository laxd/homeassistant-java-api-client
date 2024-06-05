package uk.laxd.homeassistantclient.rest

import jakarta.inject.Named
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.service.annotation.GetExchange
import uk.laxd.homeassistantclient.model.json.HomeAssistantEntity

@Named
interface HomeAssistantRestClient {

    @GetExchange("/")
    String isRunning()

    @GetExchange("/states")
    List<HomeAssistantEntity> getAllEntities()

    /**
     * Retrieves a generic entity
     */
    @GetExchange("/states/{id}")
    HomeAssistantEntity getEntity(@PathVariable("id") String id)
}