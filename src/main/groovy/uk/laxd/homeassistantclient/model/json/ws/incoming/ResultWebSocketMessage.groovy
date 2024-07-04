package uk.laxd.homeassistantclient.model.json.ws.incoming

class ResultWebSocketMessage extends ResponseWebSocketMessage {

    boolean success
    String error
    Map<String, Object> result

    @Override
    String getType() {
        return "result"
    }
}
