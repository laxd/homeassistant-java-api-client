# A Home Assistant Java client

## Usage

Instantiate a new instance of the HomeAssistantClient.

`HomeAssistantUrl` should be the base URL of the home assistant server without any trailing path e.g. `https://homeassistant.example.com` or `http://192.168.10.2:8123`

`longLivedToken` should be a long lived access token

```java
HomeAssistantClient client = HomeAssistantClient.createClient(homeAssistantUrl, longLivedToken);
```

Query for home assistant entities

```java
Entity bedroomLight = client.getEntity("light.bedroom");

if (bedroomLight.state == "On") {
    System.out.println("The bedroom light is on");
    System.out.println("It was last changed: " + bedroomLight.lastChanged)
}
```

Home assistant events can be listened for as well

```java
client.onStateChanged((stateChangedEvent) -> {
    if (stateChangedEvent.entityId == "light.bedroom") {
        System.out.println("Bedroom light changed! " + stateChangedEvent.newState)
    }
})

// Or, to listen to arbitrary events
client.onEvent("whatever_event_you_want", (event) -> {
    ...
})
```