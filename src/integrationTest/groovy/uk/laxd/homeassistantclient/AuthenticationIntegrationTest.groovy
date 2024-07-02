package uk.laxd.homeassistantclient

import uk.laxd.homeassistantclient.client.HomeAssistant
import uk.laxd.homeassistantclient.ws.exception.AuthenticationFailureException

class AuthenticationIntegrationTest extends AbstractIntegrationTest {

    String INVALID_TOKEN = "ABC123"

    def "Test authentication failure"() {
        given:
        String url = getClientUrl()

        when:
        HomeAssistant.createClient().connect(url, INVALID_TOKEN)

        then:
        def exception = thrown AuthenticationFailureException
        exception.message == "Failed to authenticate with ${url}: Invalid access token or password"
    }
}
