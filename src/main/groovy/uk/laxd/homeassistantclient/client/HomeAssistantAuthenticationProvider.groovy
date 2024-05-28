package uk.laxd.homeassistantclient.client

import jakarta.inject.Named

@Named
class HomeAssistantAuthenticationProvider {

    private HomeAssistantAuthentication authentication

    void authenticate(String url, String token) {
        this.authentication = new HomeAssistantAuthentication(url, token)
    }

    HomeAssistantAuthentication getAuthentication() {
        authentication
    }

}