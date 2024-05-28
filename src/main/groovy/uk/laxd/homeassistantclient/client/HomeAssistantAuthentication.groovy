package uk.laxd.homeassistantclient.client

import groovy.transform.TupleConstructor

/**
 * Authentication container for REST and WebSocket APIs, to be populated at client start
 * and then maintains a reference to the URL and Token provided in case e.g. the
 * WebSocket connection needs to be re-authenticated.
 */
@TupleConstructor
class HomeAssistantAuthentication {
    String url
    String token
}