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

## Triggers

Some types of [triggers](https://www.home-assistant.io/docs/automation/trigger/) are supported, see the following list
for supported types:

- Event trigger
- Home Assistant trigger
- MQTT trigger
- Numeric state trigger
- State trigger :heavy_check_mark:
- Sun trigger
- Tag trigger
- Template trigger :heavy_check_mark:
- Time trigger :heavy_check_mark:
- Time pattern trigger :heavy_check_mark:
- Persistent notification trigger
- Webhook trigger
- Zone trigger
- Geolocation trigger
- Device triggers
- Calendar trigger
- Sentence trigger

These can be used as follows. Multiple triggers are supported

```java
def trigger = TriggerBuilder.onStateChange("light.bedroom", "light.kitchen")
    .from("off")
    .to("on")
    .duration(Duration.ofSeconds(5))
    .build()

client.on(trigger, (triggerEvent) -> {
    println("Bedroom OR kitchen light turned on for 5 seconds!")
})
```