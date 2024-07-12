package uk.laxd.homeassistantclient.timeout

import jakarta.inject.Named
import jakarta.inject.Singleton

import java.util.concurrent.Future
import java.util.concurrent.TimeUnit

@Named
@Singleton
class TimeoutServiceImpl implements TimeoutService {

    private static final Integer DEFAULT_TIMEOUT_SECONDS = 10
    private static final TimeUnit DEFAULT_TIMEOUT_UNITS = TimeUnit.SECONDS

    private Integer timeout = DEFAULT_TIMEOUT_SECONDS
    private TimeUnit timeoutUnits = DEFAULT_TIMEOUT_UNITS

    @Override
    void setTimeout(Integer timeout, TimeUnit timeoutUnits) {
        this.timeout = timeout
        this.timeoutUnits = timeoutUnits
    }

    @Override
    Integer getTimeout() {
        timeout
    }

    @Override
    TimeUnit getTimeoutUnits() {
        timeoutUnits
    }

    @Override
    <T> T resolveWithinTimeout(Future<T> future) {
        future.get(timeout, timeoutUnits)
    }

}
