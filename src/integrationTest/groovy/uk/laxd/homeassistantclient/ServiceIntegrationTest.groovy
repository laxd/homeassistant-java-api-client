package uk.laxd.homeassistantclient

import uk.laxd.homeassistantclient.model.domain.entity.helpers.InputNumber
import uk.laxd.homeassistantclient.model.domain.service.ServiceBuilder

class ServiceIntegrationTest extends AbstractIntegrationTest {

    def "Test manual service call works"() {
        given:
        def client = getClient()
        def inputNumber = client.getEntity("input_number.test_number_1", InputNumber)
        inputNumber.setValue(10)

        when:
        def service = ServiceBuilder.createServiceCall("input_number.increment")
                .forEntity("input_number.test_number_1")
                .build()

        client.callService(service)

        then:
        client.getEntity("input_number.test_number_1", InputNumber).state == 15.0
    }

}
