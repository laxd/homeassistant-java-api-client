package uk.laxd.homeassistantclient.model.json.trigger.jackson

import com.fasterxml.jackson.core.JsonGenerator
import com.fasterxml.jackson.databind.JsonSerializer
import com.fasterxml.jackson.databind.SerializerProvider

import java.time.Duration

class DurationSerialiser extends JsonSerializer<Duration> {

    @Override
    void serialize(Duration value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        def hours = value.toHoursPart().toString().padLeft(2, '0')
        def minutes = value.toMinutesPart().toString().padLeft(2, '0')
        def seconds = value.toSecondsPart().toString().padLeft(2, '0')
        gen.writeString("$hours:$minutes:$seconds")
    }
}
