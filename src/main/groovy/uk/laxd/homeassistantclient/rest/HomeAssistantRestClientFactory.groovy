package uk.laxd.homeassistantclient.rest

import jakarta.inject.Named
import org.springframework.web.client.RestClient
import org.springframework.web.client.support.RestClientAdapter
import org.springframework.web.service.invoker.HttpServiceProxyFactory

@Named
class HomeAssistantRestClientFactory {

    HomeAssistantRestClient createRestClient(String url, String token) {
        def restClient = RestClient.builder()
            .baseUrl("${url}/api")
            .defaultHeaders { h -> h.setBearerAuth(token) }
            .build()
        def adapter = RestClientAdapter.create(restClient)
        def factory = HttpServiceProxyFactory.builderFor(adapter).build()

        factory.createClient(HomeAssistantRestClient)
    }

}
