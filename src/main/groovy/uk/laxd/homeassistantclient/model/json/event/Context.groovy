package uk.laxd.homeassistantclient.model.json.event

import com.fasterxml.jackson.annotation.JsonProperty
import groovy.transform.EqualsAndHashCode
import groovy.transform.ToString

/**
 * Represents the context of a given event and related changes. Changes that happen as a result
 * of another change (e.g. an automation changes the state of something, that was triggered from a state change)
 * share the same {@code parentId}
 * See https://data.home-assistant.io/docs/context/ for more details.
 */
@ToString
@EqualsAndHashCode
class Context {

    /**
     * Unique identifier for this particular state as part of this context chain
     */
    @JsonProperty("id")
    String id

    /**
     * The context ID (e.g. {@link #id} of the state change that started this context chain
     */
    @JsonProperty("parent_id")
    String parentId

    /**
     * The user that initiated the change
     */
    @JsonProperty("user_id")
    String userId

}
