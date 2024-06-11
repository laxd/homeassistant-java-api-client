# Integration tests

Integration tests run against an instance of home assistant with the config
from `test_config`. This directory provides a basic HA setup with the setup
completed, a user created (username/password = `test`/`test`), and a 
long-lived access token created (`eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiI2YTliNmQwNTcyZjI0ZTZjODlhNmQwYjUxODA3MTU4NiIsImlhdCI6MTY5MjM1Mjk4NSwiZXhwIjoyMDA3NzEyOTg1fQ.sdnC7d7vjiPhWnV2xLQapwc91OuDcTQBDj6DnHapd1Y`)

Some basic configuration can be created in `test_config/configuration.yaml`
and then used in the integration tests if required.

Dummy entities can be created by using the `template` platform from home assistant
(see [here](https://www.home-assistant.io/integrations/template/) for more details)