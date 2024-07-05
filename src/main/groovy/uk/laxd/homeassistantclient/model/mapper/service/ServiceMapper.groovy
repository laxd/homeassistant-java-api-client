package uk.laxd.homeassistantclient.model.mapper.service

import jakarta.inject.Named
import uk.laxd.homeassistantclient.model.domain.service.Service
import uk.laxd.homeassistantclient.model.domain.service.TargetType
import uk.laxd.homeassistantclient.model.json.service.JsonService

@Named
class ServiceMapper {

    JsonService map(Service service) {
        JsonService jsonService = new JsonService(service.type)

        // TODO: Map entities, devices and areas to a single target map
        service.targets.findAll { it.type == TargetType.ENTITY }
                .each { jsonService.target.entities << it.value }

        service.targets.findAll { it.type == TargetType.DEVICE }
                .each { jsonService.target.devices << it.value}

        service.targets.findAll { it.type == TargetType.AREA }
                .each { jsonService.target.areas << it.value}

        jsonService.serviceData = service.data

        jsonService
    }

}
