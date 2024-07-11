package uk.laxd.homeassistantclient.model.json.ws.incoming

class ResultWebSocketMessage extends ResponseWebSocketMessage {

    boolean success
    Map<String, Object> error
    Map<String, Object> result

    final String type = "result"

}

