package uk.laxd.homeassistantclient.examples

class PingToCheckConnection extends AbstractExample {

    static void main(String[] args) {
        def client = createClient()

        // We can ping the server to ensure it's up
        def responseMessage = client.ping()
        println("Ping response: ${responseMessage.response}")
    }


}
