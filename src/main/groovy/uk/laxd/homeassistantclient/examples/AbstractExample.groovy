package uk.laxd.homeassistantclient.examples

import uk.laxd.homeassistantclient.client.HomeAssistant
import uk.laxd.homeassistantclient.client.HomeAssistantClient

import java.nio.file.Files
import java.nio.file.Paths

abstract class AbstractExample {

    static void loadProperties() {
        def props = System.getProperties()
        var envFile = Paths.get(".env");
        try (var inputStream = Files.newInputStream(envFile)) {
            props.load(inputStream);
        }
    }

    static HomeAssistantClient createClient() {
        loadProperties()

        def url = System.getProperty("HOME_ASSISTANT_URL")
        def token = System.getProperty("HOME_ASSISTANT_TOKEN")

        return HomeAssistant.createClient()
                .connect(url, token)
    }

}
