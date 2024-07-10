package uk.laxd.homeassistantclient.model.mapper.trigger

import uk.laxd.homeassistantclient.model.domain.trigger.For

class ForMapper {

    Object mapForToObject(For f) {
        if (f && f.duration) {
            return f.duration
        }
        else if (f && (f.hours || f.minutes || f.seconds)) {
            def fr = [:]

            if (f.hours) {
                fr["hours"] = f.hours
            }

            if (f.minutes) {
                fr["minutes"] = f.minutes
            }

            if (f.seconds) {
                fr["seconds"] = f.seconds
            }

            fr
        }

        null
    }

}
