package uk.laxd.homeassistantclient.model.domain.entity.state

class State {

    public static final State ON = new State(true)
    public static final State OFF = new State(false)

    boolean on

    private State(boolean on) {
        this.on = on
    }

    @Override
    String toString() {
        on ? "on" : "off"
    }

}
