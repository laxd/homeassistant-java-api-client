package uk.laxd.homeassistantclient.model.trigger

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.annotation.JsonUnwrapped

import java.time.Duration

class For {

    For(Duration duration) {
        this.duration = duration
        this.hours = null
        this.minutes = null
        this.seconds = null
    }

    For(String hours, String minutes, String seconds) {
        this.hours = hours
        this.minutes = minutes
        this.seconds = seconds
        this.duration = null
    }

    @JsonProperty("for")
    @JsonUnwrapped
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    final Duration duration

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    final String hours

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    final String minutes

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    final String seconds

}
