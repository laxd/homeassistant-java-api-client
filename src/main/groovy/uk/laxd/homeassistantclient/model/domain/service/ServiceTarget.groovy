package uk.laxd.homeassistantclient.model.domain.service

import groovy.transform.TupleConstructor

@TupleConstructor
class ServiceTarget {

    TargetType type
    String value

}

enum TargetType {
    AREA,
    DEVICE,
    ENTITY
}
