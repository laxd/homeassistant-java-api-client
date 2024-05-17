package uk.laxd.homeassistantclient.spring

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class ObjectMapperFactory {

    @Bean
    ObjectMapper createObjectMapper() {
        ObjectMapper mapper = new ObjectMapper()
        mapper.registerModule(new JavaTimeModule())

        mapper
    }

}
