/**
 * Contains everything that is needed to connect to, send messages to, and parse messages from a Home Assistant
 * web socket connection.
 * <p>
 * Messages should contain only WebSocket specific information, and wrap any states, information or other details
 * pertaining to the Home Assistant server in a separate class, to separate concerns.
 */
package uk.laxd.homeassistantclient.ws;