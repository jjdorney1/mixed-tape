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
    public String addUrl(Model model, HttpSession session) {
        Api api = (Api) session.getAttribute("api");
        userService.refreshToken(api);
        model.addAttribute()
        return "redirect:/playlist";}

    @RequestMapping(path = "/instructions")
    public String instructions( Model model, HttpSession session) {
        Api api = (Api) session.getAttribute("api");
        userService.refreshToken(api);
        User user = userService.getUser(api);
        String userId = user.getId();
        model.addAttribute("userId", userId);
        return "instructions";}

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

        return "redirect:/instructions";
    }

    @RequestMapping(path="/test")
    public String test(Model model, HttpSession session) throws InterruptedException {

        // api session initialization
        Api api = (Api) session.getAttribute("api");

        // list to hold the tracks from library and playlists
        ArrayList<String> userLibraryTracks = new ArrayList<>();
        ArrayList<String> userPlaylistTracks;

        // object to hold image data
        Image image = new Image();

        // object to hold user id
        String userId;


        // image url for user
        String imageUrl = "profile_default.jpg";

        // list of image data
        List<Image> imageData;

//        ArrayList<String> trackArrayList = userService.getSavedTracks(api);

//        model.addAttribute("trackArrayList", trackArrayList);

        model.addAttribute("imageUrl", imageUrl);
        model.addAttribute("image", image);

        //Track track = userService.getUserSavedTracks(api).get(1).getTrack();

        //model.addAttribute("track", track);
        //302a1bef452882483a8881e827e24d28bbda969b

        // creates refresh token from api
        userService.refreshToken(api);

        // gets user based on their data and adds to model
        User user = userService.getUser(api);
        model.addAttribute("user", user);

        // sets user id based on the user logged in
        userId = user.getId();

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

        // gets the user's saved tracks from their library
        try {
            // if they have some saved tracks in their library
            if(api.getMySavedTracks().build().get().getItems().size() != 0) {

                // calls get tracks method and adds them to the model
                userLibraryTracks = userService.getSavedTracks(api);
                model.addAttribute("userLibraryTracks", userLibraryTracks);
            } else {

                // no saved tracks in library, adds none value to list so it's not empty
                userLibraryTracks.add("NONE");
            }
            // catch exceptions
        } catch (IOException | WebApiException e) {
            e.printStackTrace();
        }

        // gets the user's saved tracks from their playlists
        userPlaylistTracks = userService.getSavedPlaylists(api, userId);
        model.addAttribute("userPlaylistTracks", userPlaylistTracks);

        // return to page
        return "test";
    }
}
