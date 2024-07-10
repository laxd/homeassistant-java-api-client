package uk.laxd.homeassistantclient.model.domain.service

class ServiceType {

    public static final String TURN_ON = "homeassistant.turn_on"
    public static final String TURN_OFF = "homeassistant.turn_off"
    public static final String TOGGLE = "homeassistant.toggle"

    // Home Assistant specific services
    public static final String CHECK_CONFIG = "homeassistant.check_config"
    public static final String RELOAD_ALL = "homeassistant.reload_all"
    public static final String RELOAD_CUSTOM_TEMPLATES = "homeassistant.reload_custom_templates"
    public static final String RELOAD_CONFIG_ENTRY = "homeassistant.reload_config_entry"
    public static final String RELOAD_CORE_CONFIG = "homeassistant.reload_core_config"
    public static final String RESTART = "homeassistant.restart"
    public static final String STOP = "homeassistant.stop"
    public static final String SET_LOCATION = "homeassistant.set_location"
    public static final String UPDATE_ENTITY = "homeassistant.update_entity"
    public static final String SAVE_PERSISTENT_STATE = "homeassistant.save_persistent_states"

    // CLimate specific services
    public static final String SET_AUX_HEAT = "climate.set_aux_heat"
    public static final String SET_MODE = "climate.set_preset_mode"
    public static final String SET_TEMP = "climate.set_temperature"
    public static final String SET_HUMIDITY = "climate.set_humidity"
    public static final String SET_FAN_MODE = "climate.set_fan_mode"
    public static final String SET_HVAC_MODE = "climate.set_hvac_mode"
    public static final String SET_SWING_MODE = "climate.set_swing_mode"

    // Helpers
    // Select
    public static final String SELECT_OPTION = "input_select.select_option"
    public static final String SET_OPTIONS = "input_select.set_options"
    public static final String SELECT_FIRST = "input_select.select_first"
    public static final String SELECT_LAST = "input_select.select_last"
    public static final String SELECT_NEXT = "input_select.select_next"
    public static final String SELECT_PREVIOUS = "input_select.select_previous"
    public static final String RELOAD_INPUT_SELECT = "input_select.reload"

    // Date/Time
    public static final String SET_DATETIME = "input_datetime.set_datetime"
    public static final String RELOAD_INPUT_DATETIME = "input_datetime.reload"

}
