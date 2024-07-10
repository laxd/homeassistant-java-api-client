package uk.laxd.homeassistantclient.model.json.ws.incoming

import com.fasterxml.jackson.annotation.JsonProperty

/**
 * A message in response to a {@link uk.laxd.homeassistantclient.model.json.ws.outgoing.SubscriptionWebSocketMessage}
 * that we sent previously.
 */
abstract class ResponseWebSocketMessage extends IncomingWebSocketMessage {

    @JsonProperty("id")
    Integer subscriptionId

    @Override
    abstract String getType()

}
