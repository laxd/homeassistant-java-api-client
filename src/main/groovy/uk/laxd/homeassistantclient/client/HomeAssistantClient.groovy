package uk.laxd.homeassistantclient.client

import uk.laxd.homeassistantclient.client.exception.NoSuchEntityException
import uk.laxd.homeassistantclient.events.HomeAssistantEventListener
import uk.laxd.homeassistantclient.model.domain.entity.Entity
import uk.laxd.homeassistantclient.model.domain.response.HomeAssistantPongMessage
import uk.laxd.homeassistantclient.model.domain.trigger.Trigger
import uk.laxd.homeassistantclient.model.json.event.Event
import uk.laxd.homeassistantclient.model.json.event.TriggerEvent

interface HomeAssistantClient {

    /**
     * Sends a message to the Home Assistant server, confirming it is up and accepting connections, and that
     * authorisation is correct.
     * @return A HomeAssistantPongMessage containing the response from the server ('pong')
     */
    HomeAssistantPongMessage ping()

    /**
     * Retrieves the given entity from the Home Assistant server.
     * @param entityId An entity ID, including the domain e.g. 'light.kitchen'
     * @return An {@link Entity} referring to the requested ID, or a {@link NoSuchEntityException} if the
     *  entity does not exist.
     */
    Entity getEntity(String entityId) throws NoSuchEntityException

    /**
     * Register the given listener to trigger whenever the event given is fired in the Home Assistant server.
     * @param eventType The event to execute the given listener on
     * @param listener Listener to execute
     */
    void onEvent(String eventType, HomeAssistantEventListener<Event> listener)

    /**
     * Register the given listener to trigger whenever the given {@link Trigger} occurs. The trigger can be
     * built using the {@link uk.laxd.homeassistantclient.model.domain.trigger.builder.TriggerBuilder}.
     *
     * Multiple triggers can be registered to the same listener by using {@link #on(Collection, HomeAssistantEventListener)}.
     * @param trigger Trigger to fire on
     * @param listener Listener to fire when trigger is triggered
     */
    void on(Trigger trigger, HomeAssistantEventListener<TriggerEvent> listener)

    /**
     * Register the given listener to trigger whenever ANY of the given {@link Trigger}s occur.
     *
     * @see #on(Trigger, HomeAssistantEventListener) for more details
     * @param triggers Collection of Triggers to fire on
     * @param listener Listener to fire when any triggers are triggered
     */
    void on(Collection<Trigger> triggers, HomeAssistantEventListener<TriggerEvent> listener)

    /**
     * Attempts to turn the given entity on. Any entity that supports being turned on can be turned on with this
     * @param entityId Entity ID, including domain e.g. "light.kitchen"
     */
    void turnOn(String entityId)

    /**
     * Attempts to turn the given entity off. Any entity that supports being turned off can be turned off with this
     * @param entityId Entity ID, including domain e.g. "light.kitchen"
     */
    void turnOff(String entityId)

    /**
     * Attempts to toggle the given entity. Any entity that supports being toggled can be toggled with this
     * @param entityId Entity ID, including domain e.g. "light.kitchen"
     */
    void toggle(String entityId)

}
