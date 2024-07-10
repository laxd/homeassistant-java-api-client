package uk.laxd.homeassistantclient.ws

import jakarta.inject.Named

import java.util.concurrent.atomic.AtomicInteger

@Named
class AtomicMonotonicIdGenerator implements IdGenerator {

    AtomicInteger nextId = new AtomicInteger(1)

    @Override
    int generateId() {
        nextId.getAndIncrement()
    }

}
