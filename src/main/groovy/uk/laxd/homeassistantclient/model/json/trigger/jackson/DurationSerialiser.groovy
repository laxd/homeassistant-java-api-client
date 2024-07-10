package uk.laxd.homeassistantclient.model.json.trigger.jackson

import com.fasterxml.jackson.core.JsonGenerator
import com.fasterxml.jackson.databind.JsonSerializer
import com.fasterxml.jackson.databind.SerializerProvider

import java.time.Duration

class DurationSerialiser extends JsonSerializer<Duration> {

    private static final int DIGIT_COUNT = 2
    private static final String PADDING_CHAR = '0'

    @Override
    void serialize(Duration value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        def hours = value.toHoursPart().toString().padLeft(DIGIT_COUNT, PADDING_CHAR)
        def minutes = value.toMinutesPart().toString().padLeft(DIGIT_COUNT, PADDING_CHAR)
        def seconds = value.toSecondsPart().toString().padLeft(DIGIT_COUNT, PADDING_CHAR)
        gen.writeString("$hours:$minutes:$seconds")
    }

}

