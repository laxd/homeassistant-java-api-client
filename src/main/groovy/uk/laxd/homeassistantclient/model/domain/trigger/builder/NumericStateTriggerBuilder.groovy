package uk.laxd.homeassistantclient.model.domain.trigger.builder

import uk.laxd.homeassistantclient.model.domain.trigger.For
import uk.laxd.homeassistantclient.model.domain.trigger.NumericStateTrigger

import java.time.Duration

class NumericStateTriggerBuilder extends TriggerBuilder<NumericStateTrigger> {

    /**
     * Trigger when the state of the entity (or attribute, if {@link #forAttribute} is called) is
     * above this value.
     * <p>
     * This value can either be a static value e.g. 60, or a reference to another entity to make this
     * trigger more dynamic
     * <p>
     * If {@link #below} is also specified, the state of the entity must be between both values
     * for this trigger to fire.
     * <p>
     * @param above Value the state must be above to trigger.
     */
    TriggerBuilder above(String above) {
        this.result.above = above
        this
    }

    /**
     * Trigger when the state of the entity (or attribute, if {@link #forAttribute} is called) is
     * below this value.
     * <p>
     * This value can either be a static value e.g. 60, or a reference to another entity to make this
     * trigger more dynamic
     * <p>
     * If {@link #above} is also specified, the state of the entity must be between both values
     * for this trigger to fire.
     * <p>
     * @param below Value the state must be below to trigger.
     */
    TriggerBuilder below(String below) {
        this.result.below = below
        this
    }

    TriggerBuilder duration(Duration duration) {
        this.result.duration = new For(duration)
        this
    }

    /**
     * Change this trigger to inspect this attribute, instead of state of the entity.
     * @param attributeName Attribute name to check instead of state
     */
    TriggerBuilder forAttribute(String attributeName) {
        this.result.attribute = attributeName
        this
    }

    /**
     * Set a template to parse to compare to {@link #below} and {@link #above}.
     * <p>
     * The state object of the entity is contained in a variable "state" and can be used e.g.
     * {@code state.state | float * 9 / 5 + 32 } or
     * {@code state.attributes.current_temperature - state.attributes.set_point }
     * @param valueTemplate A template definition (i.e. A statement surrounded by {{ }}) to use instead
     *  of the state of the entity to compare to below/above.
     */
    TriggerBuilder forValueTemplate(String valueTemplate) {
        this.result.valueTemplate = valueTemplate
        this
    }

}
