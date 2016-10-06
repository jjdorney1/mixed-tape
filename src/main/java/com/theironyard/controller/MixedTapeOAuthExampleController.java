package com.theironyard.controller;

import com.theironyard.bean.TokenRequest;
import com.theironyard.bean.TokenResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.Base64;
import java.util.Random;

@Controller
public class MixedTapeOAuthExampleController {

    @Value("${spotify.scope}")
    private String scope;

    @Value("${spotify.auth-url}")
    private String authUrl;

    @Value("${spotify.client-id}")
    private String clientId;

    @Value("${spotify.client-secret}")
    private String clientSecret;

    @Value("${spotify.redirect-uri}")
    private String redirectUri;

    @Value("${spotify.state-key}")
    private String stateKey;

    @Value("${spotify.api-token-url}")
    private String apiTokenUrl;

    @RequestMapping(path = "/")
    public String doSomething(){
        return "index";
    }



    @RequestMapping(path = "/login")
    public String doLogin(HttpServletResponse response){
        String state = String.valueOf(new Random().nextLong());

        response.addCookie(new Cookie(stateKey, state));

        return "redirect:" + authUrl + "?" + "response_type=code&" +
                "client_id=" + this.clientId +
                "&scope=" + this.scope +
                "&redirect_uri=" + this.redirectUri +
                "&state=" + state;
    }

    @RequestMapping(path = "/callback")
    public String doCallback(String code, String state, @CookieValue(value = "${spotify.state-key}", defaultValue = "") String storedState){

        if(!state.equals(storedState)){
            return "redirect:/error";
        } else {
            TokenRequest request = new TokenRequest(code, this.redirectUri, "authorization_code");
            RestTemplate spotifyApi = new RestTemplate();

            HttpHeaders headers = new HttpHeaders();
            headers.add("Authorization", "Basic " + Base64.getEncoder().encodeToString((this.clientId + ":" + this.clientSecret).getBytes()));
            headers.setContentType(MediaType.APPLICATION_JSON);

            HttpEntity<String> entity = new HttpEntity<String>("parameters", headers);
            spotifyApi.put(authUrl, entity);

            TokenResponse response = spotifyApi.postForObject(this.apiTokenUrl, request, TokenResponse.class);

        }

        return "index";
    }
}
