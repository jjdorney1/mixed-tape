package com.theironyard.controller;

import com.theironyard.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

/**
 * Created by davehochstrasser on 10/3/16.
 */
@Controller
public class MixedTapeController {

    @Autowired
    UserService userService;
}
