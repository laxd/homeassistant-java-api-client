# A Home Assistant Java client

## Usage

Instantiate a new instance of the HomeAssistantClient.

`HomeAssistantUrl` should be the base URL of the home assistant server without any trailing path e.g. `https://homeassistant.example.com` or `http://192.168.10.2:8123`

`longLivedToken` should be a long lived access token

```groovy
def client = HomeAssistantClient.createClient(homeAssistantUrl, longLivedToken);
```

Query for home assistant entities

```groovy
def bedroomLight = client.getEntity("light.bedroom")

if (bedroomLight.state == "On") {
    System.out.println("The bedroom light is on")
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


#### State trigger 
```groovy
def trigger = TriggerBuilder.onStateChange("light.bedroom", "light.kitchen")
    .from("off")
    .to("on")
    .duration(Duration.ofSeconds(5))
    .build()

client.on(trigger, (triggerEvent) -> {
    println("Bedroom OR kitchen light turned on for 5 seconds!")
})
```

#### Time trigger
```groovy
def timeTrigger = TriggerBuilder.dailyAt("15:00")
        .andAt("22:00:00")
        .build()

client.on(timeTrigger, (triggerEvent) -> {
    // Something that needs to run at 15:00 or 22:00
    println("Triggered!")
})
```

#### Time pattern trigger
```groovy
def every10Seconds = TriggerBuilder.timePattern().every(10, TimeUnit.SECONDS)

client.on(every10Seconds, (triggerEvent) -> {
    println("Every 10 seconds...")
})
```

#### Template trigger
```groovy
def template = TriggerBuilder.valueTemplate("{{ is_state('light.bedroom', 'on') }}")
        .build()

client.on(template, (event) -> {
    println("Triggering when bedroom light turns on")
})
```