package uk.laxd.homeassistantclient.model.json.ws.incoming

class ResultWebSocketMessage extends ResponseWebSocketMessage {

    boolean success
    String error
    Map<String, Object> result

    final String type = "result"

}

