package uk.laxd.homeassistantclient.model.domain.entity.light

import groovy.transform.InheritConstructors
import uk.laxd.homeassistantclient.model.domain.entity.BaseEntity
import uk.laxd.homeassistantclient.model.domain.entity.state.State

@InheritConstructors
class LightEntity extends BaseEntity<State> {

    void setBrightness(short brightness) {
        this.client.turnOn(this.entityId, ["brightness" : brightness])
    }

    short getBrightness() {
        (short) (attributes["brightness"] ?: 0)
    }

    short getBrightnessPercent() {
        (brightness / 255) * 100
    }

    short getColourTemperature() {
        (short) (attributes["color_temp"] ?: 0)
    }

    /**
     * Uses mireds? Range of 154-500
     * @param colourTemperature the colour temperature to set in mireds
     */
    void setColourTemperature(short colourTemperature) {
        this.client.turnOn(this.entityId, ["color_temp" : colourTemperature])
    }

    Colour getColour() {
        def rgb = (attributes["rgb_color"] ?: [0, 0, 0]) as short[]

        Colour.rgb(*rgb)
    }

    void setColour(Colour colour) {
        this.client.turnOn(this.entityId, ["rgb_color" : [colour.red, colour.green, colour.blue]])
    }

}
