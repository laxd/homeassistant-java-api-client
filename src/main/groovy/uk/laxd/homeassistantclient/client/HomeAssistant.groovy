package uk.laxd.homeassistantclient.client

import org.springframework.context.annotation.AnnotationConfigApplicationContext
import uk.laxd.homeassistantclient.rest.HomeAssistantRestClientFactory
import uk.laxd.homeassistantclient.spring.HomeAssistantClientConfiguration
import uk.laxd.homeassistantclient.ws.HomeAssistantWebSocketClient

class HomeAssistant {
    static HomeAssistant createClient() {
        return new HomeAssistant()
    }

    /**
     * Create a new client for home assistant.
     *
     * The url should be a URL that points to the base of the home assistant instance, don't
     * include any paths e.g. /api or /lovelace
     *
     * The token should be a <a href="https://developers.home-assistant.io/docs/auth_api/#long-lived-access-token">
     * Long-Lived access token</a>.
     */
    HomeAssistantClient connect(String url, String token) {
        def context = new AnnotationConfigApplicationContext(HomeAssistantClientConfiguration.class)

        def wsClient = context.getBean(HomeAssistantWebSocketClient.class)
        wsClient.connect(url, token)

        def restClient = context.getBean(HomeAssistantRestClientFactory)
                .createRestClient(url, token)

        new HomeAssistantClientImpl(restClient, wsClient)
    }
}
