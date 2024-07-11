package uk.laxd.homeassistantclient.model.domain.entity.state.converter

import spock.lang.Specification
import uk.laxd.homeassistantclient.model.domain.entity.state.State
import uk.laxd.homeassistantclient.model.json.HomeAssistantEntity

class StateConverterSpec extends Specification {

    def "OnOff state converter converts states correctly"() {
        given:
        def converter = new OnOffStateConverter()

        when:
        def entity = new HomeAssistantEntity()
        entity.state = state

        then:
        converter.convertState(entity) == expectedValue

        where:
        state | expectedValue
        "on" | State.ON
        "off" | State.OFF
        "true" | State.ON
        "ON" | State.ON
        "awlefjahef" | State.OFF
    }

    def "Numeric state converter converts states correctly"() {
        given:
        def converter = new NumericStateConverter()

        when:
        def entity = new HomeAssistantEntity()
        entity.state = state

        then:
        converter.convertState(entity) == expectedValue

        where:
        state | expectedValue
        "15" | 15.0f
        "" | 0.0f
        "10.8" | 10.8f
        null | 0.0f
    }

    def "String state converter converts states correctly"() {
        given:
        def converter = new StringStateConverter()

        when:
        def entity = new HomeAssistantEntity()
        entity.state = state

        then:
        converter.convertState(entity) == expectedValue

        where:
        state | expectedValue
        null | null
        "" | ""
        "ABC" | "ABC"
    }

}
