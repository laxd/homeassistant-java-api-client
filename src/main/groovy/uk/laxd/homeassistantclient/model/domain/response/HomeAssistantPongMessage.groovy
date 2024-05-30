package uk.laxd.homeassistantclient.model.domain.response

import groovy.transform.TupleConstructor

@TupleConstructor
class HomeAssistantPongMessage extends HomeAssistantMessage {
    String response
}
