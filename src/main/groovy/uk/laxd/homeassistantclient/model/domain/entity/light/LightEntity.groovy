package uk.laxd.homeassistantclient.model.domain.entity.light

import groovy.transform.InheritConstructors
import uk.laxd.homeassistantclient.model.domain.entity.GenericEntity

@InheritConstructors
class LightEntity extends GenericEntity {

    void setBrightness(short brightness) {
        this.wsClient.turnOn(this.entityId, ["brightness": brightness])
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
     * @param colourTemperature
     */
    void setColourTemperature(short colourTemperature) {
        this.wsClient.turnOn(this.entityId, ["color_temp": colourTemperature])
    }

    Colour getColour() {
        def rgb = (attributes["rgb_color"] ?: [0,0,0]) as short[]

        Colour.rgb(*rgb)
    }

    void setColour(Colour colour) {
        this.wsClient.turnOn(this.entityId, ["rgb_color": [colour.red, colour.green, colour.blue]])
    }

}
