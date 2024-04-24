package com.elephants.ShortenUrl.users.auth;

import com.elephants.ShortenUrl.request_response.UserRequest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/auth")
public class AuthController {

    AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @ResponseBody
    @PostMapping(value = "/login")
    public ResponseEntity login(@RequestBody UserRequest authRequest)  {
        return authService.loginInSystem(authRequest);
    }
    @ResponseBody
    @PostMapping(value = "/register")
    public ResponseEntity register(@RequestBody UserRequest userRequest)  {
        return authService.registerInSystem(userRequest);
    }
    @GetMapping(value = "/logout")
    public ResponseEntity logout()  {
        return ResponseEntity.ok().header(HttpHeaders.AUTHORIZATION, "").build();
    }


}