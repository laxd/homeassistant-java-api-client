package uk.laxd.homeassistantclient.model.json.ws.incoming

import groovy.transform.ToString

@ToString
class PongWebSocketMessage extends ResponseWebSocketMessage {

    final String type = "pong"

}
