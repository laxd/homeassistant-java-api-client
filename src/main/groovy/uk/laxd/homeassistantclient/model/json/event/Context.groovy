package uk.laxd.homeassistantclient.model.json.event

import com.fasterxml.jackson.annotation.JsonProperty

class Context {

    @JsonProperty("id")
    String id

    @JsonProperty("parent_id")
    String parentId

    @JsonProperty("user_id")
    String userId

}
