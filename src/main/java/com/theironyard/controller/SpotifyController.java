package com.theironyard.controller;

import com.theironyard.bean.Search;
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

    @RequestMapping(path = "/options")
    public String getPlaylist(Model model, HttpSession session, String action, Search search, Search featuredSearch, String featuredAction) throws InterruptedException {
        String friendId = null;
        String friendImage;
        User friend = new User();
        List<com.theironyard.entity.User> featuredUsers = userService.getFeaturedUsers();
        model.addAttribute("featuredUsers", featuredUsers);
        Api api = (Api) session.getAttribute("api");
        userService.refreshToken(api);

        if (action != null && search != null) {
            friendId = userService.trimFriendId(search.getUri());
            try {
                friend = api.getUser(friendId).build().get();
            } catch (IOException | WebApiException e) {
                e.printStackTrace();
            }
            friendImage = userService.getUserImageUrl(userService.getFriend(api, friendId));
            model.addAttribute("friendImage", friendImage);
            model.addAttribute("friend", friend);

        } else if (featuredAction != null && featuredSearch != null) {
            friendId = featuredSearch.getUri();
            try {
                friend = api.getUser(friendId).build().get();
            } catch (IOException | WebApiException e) {
                e.printStackTrace();
            }
            friendImage = userService.getFeaturedUserImage(api, friendId);
            model.addAttribute("friendImage", friendImage);
            model.addAttribute("friend", friend);
        }

        // list of image data
        List<Image> imageData = userService.getUser(api).getImages();
        // object to hold image data
        Image image = new Image();
        // image url for user
        String imageUrl;

        if (imageData.size() != 0) {
            // user has an image & sets image to their profile image
            image = imageData.get(0);
            imageUrl = image.getUrl();
        } else {
            // sets default when no image
            imageUrl = "profile_default.jpg";
        }
        // adds the image data to the model
        model.addAttribute("imageUrl", imageUrl);
        model.addAttribute("image", image);

        User user = userService.getUser(api);
        model.addAttribute("user", user);
        ArrayList<String> userTracks = userService.getAllUserMusicList(userService.getSavedTracks(api), userService.getSavedPlaylists(api, user.getId()));
        model.addAttribute("userTracks", userTracks);
        ArrayList<String> friendTracks = userService.getSavedTracks(api, friend.getId());
        model.addAttribute("friend", friend);
        model.addAttribute("friendTracks", friendTracks);
        ArrayList<String> differences = userService.getMixedTapeList(userTracks, friendTracks);

        // method creates new playlist and returns the playlist id to the model
        String newPlaylistId = userService.createNewTrackPlaylist(api, user, friend, differences);
        model.addAttribute("newPlaylistId", newPlaylistId);
        String embedPlaylist = "https://embed.spotify.com/?uri=spotify%3Auser%3A" + user.getId() + "%3Aplaylist%3A" + newPlaylistId;
        model.addAttribute("embedPlaylist", embedPlaylist);
        String newPlaylistName = null;
        try {
            newPlaylistName = api.getPlaylist(user.getId(), newPlaylistId).build().get().getName();
        } catch (IOException | WebApiException e) {
            e.printStackTrace();
        }
        model.addAttribute("newPlaylistName", newPlaylistName);
        return "playlist";
    }

    @RequestMapping(path = "/instructions")
    public String instructions(Model model, HttpSession session) {
        Api api = (Api) session.getAttribute("api");
        userService.refreshToken(api);
        User user = userService.getUser(api);
        String userId = user.getId();
        model.addAttribute("userId", userId);
        return "instructions";
    }

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
    public String doCallback(String code, HttpSession session) {

        Api api = (Api) session.getAttribute("api");
        /* Make a token request. Asynchronous requests are made with the .getAsync method and synchronous requests
         * are made with the .get method. This holds for all type of requests. */
        try {
            AuthorizationCodeCredentials credentials = api.authorizationCodeGrant(code).build().get();
            // collects access token and refresh token
            api.setAccessToken(credentials.getAccessToken());
            api.setRefreshToken(credentials.getRefreshToken());
            // catches exceptions
        } catch (IOException | WebApiException e) {
            e.printStackTrace();
        }
        return "options";
    }
}
