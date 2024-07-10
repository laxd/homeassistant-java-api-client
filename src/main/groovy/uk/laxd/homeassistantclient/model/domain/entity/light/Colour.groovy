package uk.laxd.homeassistantclient.model.domain.entity.light

class Colour {

    public static final Colour RED = new Colour((short) 255, (short) 0, (short) 0)

    final short red
    final short green
    final short blue

    private Colour(short red, short green, short blue) {
        this.red = red
        this.green = green
        this.blue = blue
    }

    static Colour rgb(short r, short g, short b) {
        new Colour(r, g, b)
    }

    @Override
    String toString() {
        "[$red, $green, $blue]"
    }

}
