package uk.laxd.homeassistantclient.ws.message

import jakarta.inject.Named
import jakarta.inject.Singleton

@Singleton
@Named
// TODO: Find a better name
class WebSocketAuthenticationSync {

    final Object sync = new Object()
    boolean authenticated = false

}
