package uk.laxd.homeassistantclient.client

import uk.laxd.homeassistantclient.client.exception.InvalidEntityException
import uk.laxd.homeassistantclient.client.exception.NoSuchEntityException
import uk.laxd.homeassistantclient.events.HomeAssistantListener
import uk.laxd.homeassistantclient.model.domain.entity.Entity
import uk.laxd.homeassistantclient.model.domain.response.HomeAssistantPongMessage
import uk.laxd.homeassistantclient.model.domain.service.Service
import uk.laxd.homeassistantclient.model.domain.trigger.Trigger
import uk.laxd.homeassistantclient.model.json.event.Event
import uk.laxd.homeassistantclient.model.json.event.TriggerEvent

interface HomeAssistantClient {

    /**
     * Initiates a connection with the Home Assistant server. This method must be
     * called before any other methods on this object.
     *
     * Specifically, registers the URL and Token provided with the REST API for future calls
     * and initiates communication with the Home Assistant web socket API via an
     * {@link AuthenticationWebSocketMessage}.
     *
     * @param url Home Assistant URL to connect to. This should be the root URL of the server
     *      e.g. https://homeassistant.example.com
     * @param token A long lived access token
     */
    void connect(String url, String token)

    /**
     * Sends a message to the Home Assistant server, confirming it is up and accepting connections, and that
     * authorisation is correct.
     * @return A HomeAssistantPongMessage containing the response from the server ('pong')
     */
    HomeAssistantPongMessage ping()

    /**
     * Retrieves the given entity from the Home Assistant server.
     * @param entityId An entity ID, including the domain e.g. 'light.kitchen'
     * @return An {@link Entity} referring to the requested ID
     * @throws NoSuchEntityException if the entity does not exist.
     */
    Entity getEntity(String entityId) throws NoSuchEntityException

    /**
     * Retrieves the given entity from the Home Assistant server and attempts to cast it to
     * the given type of entity.
     * @param entityId An entity ID, including the domain e.g. 'light.kitchen'
     * @param entityClass The {@link Entity} subtype that the returned entity should be
     * @return An {@link Entity} referring to the requested ID
     * @throws NoSuchEntityException if the entity does not exist.
     * @throws InvalidEntityException If the entity ID provided does not map to an entity of type {@code entityClass}.
     *      e.g. providing sensor.motion_sensor with entity class LightEntity
     */
    <E extends Entity> E getEntity(String entityId, Class<E> entityClass) throws NoSuchEntityException, InvalidEntityException

    /**
     * Register the given listener to trigger whenever the event given is fired in the Home Assistant server.
     * @param eventType The event to execute the given listener on
     * @param listener Listener to execute
     */
    void onEvent(String eventType, HomeAssistantListener<Event> listener)

    /**
     * Register the given listener to trigger whenever the given {@link Trigger} occurs. The trigger can be
     * built using the {@link uk.laxd.homeassistantclient.model.domain.trigger.builder.TriggerBuilder}.
     *
     * Multiple triggers can be registered to the same listener by using {@link #on(Collection, HomeAssistantListener)}.
     * @param trigger Trigger to fire on
     * @param listener Listener to fire when trigger is triggered
     */
    void on(Trigger trigger, HomeAssistantListener<TriggerEvent> listener)

    /**
     * Register the given listener to trigger whenever ANY of the given {@link Trigger}s occur.
     *
     * @see #on(Trigger, HomeAssistantListener) for more details
     * @param triggers Collection of Triggers to fire on
     * @param listener Listener to fire when any triggers are triggered
     */
    void on(Collection<Trigger> triggers, HomeAssistantListener<TriggerEvent> listener)

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

    /**
     * Calls the given service.
     *
     * A {@link Service} can either be created manually, or using the {@code ServiceBuilder} class instead. e.g.
     *<pre>
     * {@code
     *     ServiceBuilder.createServiceCall("light.turn_on")
     *         .forEntity("light.bedroom")
     *         .withAttribute("brightness", 255)
     *         .build()
     * }
     * </pre>
     * @param service
     */
    void callService(Service service)
}
