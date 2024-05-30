package uk.laxd.homeassistantclient.model.json.ws.incoming

import groovy.transform.ToString

@ToString
class HomeAssistantPongMessage extends HomeAssistantResponseMessage {

    @Override
    String getType() {
        "pong"
    }
}
