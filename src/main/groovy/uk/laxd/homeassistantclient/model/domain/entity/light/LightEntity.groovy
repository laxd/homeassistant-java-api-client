package uk.laxd.homeassistantclient.model.domain.entity.light

import groovy.transform.InheritConstructors
import uk.laxd.homeassistantclient.model.domain.entity.GenericEntity

import java.util.concurrent.TimeUnit

@InheritConstructors
class LightEntity extends GenericEntity {

    void setBrightness(short brightness) {
        this.wsClient.turnOn(this.entityId, ["brightness": brightness]).get(10, TimeUnit.SECONDS)
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
        this.wsClient.turnOn(this.entityId, ["color_temp": colourTemperature]).get(10, TimeUnit.SECONDS)
    }

    Colour getColour() {
        def rgb = (attributes["rgb_color"] ?: [0,0,0]) as short[]

        Colour.rgb(*rgb)
    }

    void setColour(Colour colour) {
        this.wsClient.turnOn(this.entityId, ["rgb_color": [colour.red, colour.green, colour.blue]]).get(10, TimeUnit.SECONDS)
    }

}
