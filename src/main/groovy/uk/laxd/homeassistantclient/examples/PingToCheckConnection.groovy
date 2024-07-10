package uk.laxd.homeassistantclient.examples

import groovy.util.logging.Slf4j

@Slf4j
class PingToCheckConnection extends BaseExample {

    static void main(String[] args) {
        def client = newClient()

        // We can ping the server to ensure it's up
        def responseMessage = client.ping()
        log.info("Ping response: ${responseMessage.response}")
    }

}
