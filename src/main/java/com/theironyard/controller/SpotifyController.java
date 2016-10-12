package com.theironyard.controller;

import com.theironyard.service.UserService;
import com.wrapper.spotify.Api;
import com.wrapper.spotify.exceptions.WebApiException;
import com.wrapper.spotify.models.AuthorizationCodeCredentials;
import com.wrapper.spotify.models.Image;
import com.wrapper.spotify.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

@Controller
public class SpotifyController {

    @Value("${spotify.client-id}")
    private String clientId;

    @Value("${spotify.client-secret}")
    private String clientSecret;

    @Value("${spotify.redirectUri}")
    private String redirectUri;

    @Value("${spotify.scope}")
    private String scope;

    @Value("${spotify.stateKey}")
    private String stateKey;

    private String country = "US";

    @Autowired
    UserService userService;

    @RequestMapping(path = "/")
    public String doSomething() {
        return "index";
    }

    @RequestMapping(path = "/playlist")
    public String addUrl() {return "playlist";}

    @RequestMapping(path = "/login")
    public String doLogin(HttpSession session) {

        String state = String.valueOf(new Random().nextLong());

        session.setAttribute("state", state);

        // configure the API
        final Api api = Api.builder()
                .clientId(this.clientId)
                .clientSecret(this.clientSecret)
                .redirectURI(this.redirectUri)
                .build();

        session.setAttribute("api", api);

        // configure scope for auth
        List<String> scopes = Arrays.asList(this.scope.split(" "));

        // generate auth url for client
        String authorizeURL = api.createAuthorizeURL(scopes, state);

        // redirect to auth page
        return "redirect:" + authorizeURL;
    }

    @RequestMapping(path = "/callback")
    public String doCallback(String code, String state, HttpSession session) {

        Api api = (Api) session.getAttribute("api");
        /* Make a token request. Asynchronous requests are made with the .getAsync method and synchronous requests
         * are made with the .get method. This holds for all type of requests. */
        try {
            AuthorizationCodeCredentials credentials = api.authorizationCodeGrant(code).build().get();

            api.setAccessToken(credentials.getAccessToken());
            api.setRefreshToken(credentials.getRefreshToken());

        } catch (IOException | WebApiException e) {
            e.printStackTrace();
        }

        return "redirect:/test";
    }

    @RequestMapping(path="/test")
    public String test(Model model, HttpSession session) throws InterruptedException {

        // api session initialization
        Api api = (Api) session.getAttribute("api");

        // creates refresh token from api
        userService.refreshToken(api);

        // gets user based on their data and adds to model
        User user = userService.getUser(api);

        // object to hold user id
        String userId = user.getId();

        // ALL the users tracks in their account!
        ArrayList<String> userTracks = userService.getAllUserMusicList(userService.getSavedTracks(api), userService.getSavedPlaylists(api, userId));

        int numberOfTracks = userTracks.size();

        model.addAttribute("numberOfTracks", numberOfTracks);

        model.addAttribute("user", user);
        model.addAttribute("userTracks", userTracks);

        // object to hold image data
        Image image = new Image();

        // image url for user
        String imageUrl = "profile_default.jpg";

        // list of image data
        List<Image> imageData;

        // gets the user's image if they have one
        if(userService.getUser(api).getImages().size() != 0) {
            // user has an image
            // sets image to their profile image
            imageData = userService.getUser(api).getImages();
            image = imageData.get(0);
            imageUrl = image.getUrl();
        }
        // adds the image data to the model
        model.addAttribute("imageUrl", imageUrl);
        model.addAttribute("image", image);

        String uid = userService.trimFriendId("spotify:user:1254755551");
        model.addAttribute("uid", uid);

        userService.refreshToken(api);

        User friend = userService.getFriend(api, "1217627939");
        ArrayList<String> friendTracks = userService.getSavedTracks(api, "1217627939");
        model.addAttribute("friend", friend);
        model.addAttribute("friendTracks", friendTracks);

        // return to page
        return "test";
    }
}
