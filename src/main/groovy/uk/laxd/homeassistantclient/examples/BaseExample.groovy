package uk.laxd.homeassistantclient.examples

import uk.laxd.homeassistantclient.client.HomeAssistant
import uk.laxd.homeassistantclient.client.HomeAssistantClient

import java.nio.file.Files
import java.nio.file.Paths

class BaseExample {

    protected BaseExample() { }

    static void loadProperties() {
        def props = System.properties
        var envFile = Paths.get(".env")
        try (var inputStream = Files.newInputStream(envFile)) {
            props.load(inputStream)
        }
    }

    static HomeAssistantClient newClient() {
        loadProperties()

        def url = System.getProperty("HOME_ASSISTANT_URL")
        def token = System.getProperty("HOME_ASSISTANT_TOKEN")

        HomeAssistant.createClient().connect(url, token)
    }

}
