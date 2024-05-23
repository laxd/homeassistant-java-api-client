package uk.laxd.homeassistantclient.model.service

import spock.lang.Specification
import uk.laxd.homeassistantclient.model.domain.service.Service
import uk.laxd.homeassistantclient.model.domain.service.ServiceTarget
import uk.laxd.homeassistantclient.model.domain.service.ServiceType
import uk.laxd.homeassistantclient.model.domain.service.TargetType
import uk.laxd.homeassistantclient.model.mapper.service.ServiceMapper
import uk.laxd.homeassistantclient.spring.ObjectMapperFactory

class ServiceSerialisationSpec extends Specification {

    def "minimal service can be serialised"() {
        given:
        def service = new Service(ServiceType.TURN_ON)
        service.serviceTargets << new ServiceTarget(TargetType.ENTITY, "light.bedroom")

        when:
        def jsonService = new ServiceMapper().map(service)
        def node = new ObjectMapperFactory().createObjectMapper().valueToTree(jsonService)

        then:
        node.fieldNames().toList() == ["type", "domain", "service", "target"]
        node.get("type").textValue() == "call_service"
        node.get("domain").textValue() == "homeassistant"
        node.get("service").textValue() == "turn_on"
        node.get("target").get("entity_id").toList()[0].textValue() == "light.bedroom"
    }
}
