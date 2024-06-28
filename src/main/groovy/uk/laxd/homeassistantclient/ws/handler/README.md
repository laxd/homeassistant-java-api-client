# Design

Messages come in from the Home Assistant server, most of them will have a 
subscription ID i.e. will be in response to a message that we sent the server.

There are a few exceptions, but in general:

- Authentication required - On initial connection, HA will send us this message and we need to reply with credentials
- Authentication Response - Success/failure of authentication. Although this is in response to our earlier auth message,
it does not have an ID so need to handle this manually.

Everything else is either:

- A event happening
- A trigger occurred
- A response to setting up an event listener/trigger (telling us it was successful or not)

Note that multiple types can be returned for a single ID e.g.
we might get a result response telling us a trigger was set up, followed by that trigger being triggered. Both
need to be handled but by different listeners depending on the type.

# Implementation

1. A message handler receives the messages from HA
2. Multiple handlers should be able to be registered to handle specific messages e.g. auth
3. One handler should delegate to the listeners set up by the end user
4. Allow waiting for responses of a specific type when sending messages e.g. when setting up a trigger, allow waiting
for the result to come back

Listeners should ONLY handle messages that contain events, other messages e.g. results, auth etc should be handled by a 
MessageHandler subclass