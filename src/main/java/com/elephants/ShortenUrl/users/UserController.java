package com.elephants.ShortenUrl.users;


import com.elephants.ShortenUrl.request_response.*;
import com.elephants.ShortenUrl.urls.UrlRepository;
import com.elephants.ShortenUrl.users.auth.JwtUtil;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@Controller
@RestController
@AllArgsConstructor
@RequestMapping("/user")
public class UserController {
    CustomUserDetailsService userService;

    @GetMapping("/{username}")
    public GetAllUrlForUserResponse getAllUrlForUser(Principal principal,@PathVariable(name = "username") String username){
        return userService.getAllUrlForUserResponse(principal.getName(),username);
    }

    @PutMapping
    public UpdateUserResponse updateUser(Principal principal,@RequestBody UpdateUserRequest request){
        return userService.updateUser(principal.getName(),request);
    }

    @DeleteMapping
    public ErrorRes delete(Principal principal, @RequestParam(name = "username") String username) {
        return userService.deleteUser(principal.getName(),username);
    }
     @GetMapping("/users")
     public List<UserEntity> getAllUsers(){
        return userService.getAllUsers();
     }
}
