package uk.laxd.homeassistantclient.model.domain.trigger

import groovy.transform.EqualsAndHashCode

import java.time.Duration

/**
 * Represents a duration that a specific entity should be in a specific state for.
 *
 * Incoming messages will always have the {@link #duration} set, but outgoing messages
 * may use either constructor to create a new instance that way.
 */
@EqualsAndHashCode
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
