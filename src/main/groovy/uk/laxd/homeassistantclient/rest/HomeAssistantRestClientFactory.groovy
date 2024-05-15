package uk.laxd.homeassistantclient.rest

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Lazy
import org.springframework.web.client.RestClient
import org.springframework.web.client.support.RestClientAdapter
import org.springframework.web.service.invoker.HttpServiceProxyFactory
import uk.laxd.homeassistantclient.client.HomeAssistantAuthentication

@Configuration
@Lazy
class HomeAssistantRestClientFactory {
    @Bean
    HomeAssistantRestClient createRestClient(HomeAssistantAuthentication auth) {
        def restClient = RestClient.builder()
            .baseUrl("${auth.url}/api")
            .defaultHeaders { h -> h.setBearerAuth(auth.token) }
            .build()
        def adapter = RestClientAdapter.create(restClient)
        def factory = HttpServiceProxyFactory.builderFor(adapter).build()

        return factory.createClient(HomeAssistantRestClient.class)
    }
}