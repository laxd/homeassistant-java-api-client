package uk.laxd.homeassistantclient.timeout

import java.util.concurrent.Future
import java.util.concurrent.TimeUnit

interface TimeoutService {

    void setTimeout(Integer timeout, TimeUnit unit)

    Integer getTimeout()

    TimeUnit getTimeoutUnits()

    <T> T resolveWithinTimeout(Future<T> future)

}
