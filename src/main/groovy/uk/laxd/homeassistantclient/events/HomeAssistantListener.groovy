package uk.laxd.homeassistantclient.events

/**
 * A listener that will be notified when messages are received from the Home Assistant
 * server.
 * @param <O> Type of object to handle
 */
interface HomeAssistantListener<O> {

    abstract void handle(O object)

}
