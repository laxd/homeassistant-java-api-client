package uk.laxd.homeassistantclient.model.domain.trigger

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

    final Duration duration
    final String hours
    final String minutes
    final String seconds

}
