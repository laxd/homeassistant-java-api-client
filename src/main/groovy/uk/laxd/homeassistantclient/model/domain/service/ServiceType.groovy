package uk.laxd.homeassistantclient.model.domain.service

class ServiceType {
    public static final TURN_ON = "homeassistant.turn_on"
    public static final TURN_OFF = "homeassistant.turn_off"
    public static final TOGGLE = "homeassistant.toggle"

    // Home Assistant specific services
    public static final CHECK_CONFIG = "homeassistant.check_config"
    public static final RELOAD_ALL = "homeassistant.reload_all"
    public static final RELOAD_CUSTOM_TEMPLATES = "homeassistant.reload_custom_templates"
    public static final RELOAD_CONFIG_ENTRY = "homeassistant.reload_config_entry"
    public static final RELOAD_CORE_CONFIG = "homeassistant.reload_core_config"
    public static final RESTART = "homeassistant.restart"
    public static final STOP = "homeassistant.stop"
    public static final SET_LOCATION = "homeassistant.set_location"
    public static final UPDATE_ENTITY = "homeassistant.update_entity"
    public static final SAVE_PERSISTENT_STATE = "homeassistant.save_persistent_states"

    // CLimate specific services
    public static final SET_AUX_HEAT = "climate.set_aux_heat"
    public static final SET_MODE = "climate.set_preset_mode"
    public static final SET_TEMP = "climate.set_temperature"
    public static final SET_HUMIDITY = "climate.set_humidity"
    public static final SET_FAN_MODE = "climate.set_fan_mode"
    public static final SET_HVAC_MODE = "climate.set_hvac_mode"
    public static final SET_SWING_MODE = "climate.set_swing_mode"

    // Helpers
    // Select
    public static final SELECT_OPTION = "input_select.select_option"
    public static final SET_OPTIONS = "input_select.set_options"
    public static final SELECT_FIRST = "input_select.select_first"
    public static final SELECT_LAST = "input_select.select_last"
    public static final SELECT_NEXT = "input_select.select_next"
    public static final SELECT_PREVIOUS = "input_select.select_previous"
    public static final RELOAD_INPUT_SELECT = "input_select.reload"

    // Date/Time
    public static final SET_DATETIME = "input_datetime.set_datetime"
    public static final RELOAD_INPUT_DATETIME = "input_datetime.reload"

}