package uk.laxd.homeassistantclient

class SmokeIntegrationTest extends AbstractIntegrationTest {

    def "Basic ping test smoke test"() {
        given:
        def client = getClient()

        when:
        def pong = client.ping()

        then:
        pong.response == "pong"
    }

}
