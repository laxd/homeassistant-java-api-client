package uk.laxd.homeassistantclient.model.trigger.builder

import uk.laxd.homeassistantclient.model.trigger.TimePatternTrigger

import java.util.concurrent.TimeUnit

class TimePatternTriggerBuilder extends TriggerBuilder<TimePatternTrigger> {

    /**
     * <p>
     *     Fire this trigger when the hour matches this string (0-24, or *).
     * </p>
     * <p>
     *     This can be combined with {@link #atMinute(String)} and {@link #atSecond(String)}
     *     to set the minute/second within the hour to fire.
     * </p>
     * <p>
     *     Setting this to "*" will match every hour.
     * </p>
     * <p>
     *     Additionally, the value can be prefixed with "/" to indicate the trigger
     *     should fire everytime the current time hour is exactly divisible by
     *     {@code hours}. This can also be handled by {@link #every(int, TimeUnit)}
     * </p>
     */
    TimePatternTriggerBuilder atHour(String hours) {
        this.result.hours = hours
        this
    }

    /**
     * <p>
     *     Fire this trigger when the minute matches this string (0-60, or *).
     * </p>
     * <p>
     *     This can be combined with {@link #atSecond(String)}
     *     to set the second within the minute to fire.
     * </p>
     * <p>
     *     Setting this to "*" will match every minute.
     * </p>
     * <p>
     *     Additionally, the value can be prefixed with "/" to indicate the trigger
     *     should fire everytime the current time minute is exactly divisible by
     *     {@code minutes}. This can also be handled by {@link #every(int, TimeUnit)}
     * </p>
     */
    TimePatternTriggerBuilder atMinute(String minutes) {
        this.result.minutes = minutes
        this
    }

    /**
     * <p>
     *     Fire this trigger when the second of the current time matches this string (0-60, or *).
     * </p>
     * <p>
     *     Setting this to "*" will match every second.
     * </p>
     * <p>
     *     Additionally, the value can be prefixed with "/" to indicate the trigger
     *     should fire everytime the current time second is exactly divisible by
     *     {@code seconds}. This can also be handled by {@link #every(int, TimeUnit)}
     * </p>
     */
    TimePatternTriggerBuilder atSecond(String seconds) {
        this.result.seconds = seconds
        this
    }

    /**
     * <p>
     *     Returns a {@link TimePatternTrigger} that triggers every {@code amount} {@code timeUnit}s.
     * </p>
     * <p>
     *     The next invocation will be triggered the next time the given {@link TimeUnit} changes, e.g.
     * </p>
     * <p>
     *     Calling {@code every(1, TimeUnit.MINUTES)} will trigger when the minute changes and every 1 minute
     *     after that.
     * </p>
     */
    TimePatternTrigger every(int amount, TimeUnit timeUnit) {
        if (!isAmountValidForTimeUnit(amount, timeUnit)) {
            throw new IllegalArgumentException("Amounts must not exceed TimeUnit maximums (HOURS <= 24, MINUTES/SECONDS <= 60)")
        }

        switch (timeUnit) {
            case TimeUnit.HOURS:
                this.result.hours = "/$amount"
                break
            case TimeUnit.MINUTES:
                this.result.minutes = "/$amount"
                break
            case TimeUnit.SECONDS:
                this.result.seconds = "/$amount"
                break
            default:
                throw new IllegalArgumentException("TimeUnit value must be one of HOURS, MINUTES or SECONDS. To run something on a longer timescale, create a Time trigger and set a condition on which days to run it")
        }

        this.result
    }

    private boolean isAmountValidForTimeUnit(int amount, TimeUnit timeUnit) {
        (timeUnit in [TimeUnit.SECONDS, TimeUnit.MINUTES] && amount <= 60) ||
                (timeUnit == TimeUnit.HOURS && amount <= 24)
    }

}
