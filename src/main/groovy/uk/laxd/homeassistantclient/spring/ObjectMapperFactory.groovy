package uk.laxd.homeassistantclient.spring

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.module.SimpleModule
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import uk.laxd.homeassistantclient.model.json.trigger.jackson.DurationSerialiser

import java.time.Duration

@Configuration
class ObjectMapperFactory {

    @Bean
    ObjectMapper createObjectMapper() {
        ObjectMapper mapper = new ObjectMapper()
        // Allow parsing of LocalDateTime objects
        mapper.registerModule(new JavaTimeModule())

        SimpleModule module = new SimpleModule()
        // Fix duration objects to parse to HA compatible format of HH:mm:SS
        module.addSerializer(Duration, new DurationSerialiser())

        mapper.registerModule(module)

        mapper
    }

}
