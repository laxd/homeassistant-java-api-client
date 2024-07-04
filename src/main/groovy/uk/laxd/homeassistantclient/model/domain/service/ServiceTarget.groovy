package uk.laxd.homeassistantclient.model.domain.service

import groovy.transform.ToString
import groovy.transform.TupleConstructor

@TupleConstructor
class ServiceTarget {

    TargetType type
    String value


    @Override
    String toString() {
        return value
    }
}

