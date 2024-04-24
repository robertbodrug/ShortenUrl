package com.elephants.ShortenUrl.users.auth;

import com.elephants.ShortenUrl.users.CustomUserDetailsService;
import com.elephants.ShortenUrl.users.UserEntity;
import com.elephants.ShortenUrl.request_response.UserRequest;
import com.elephants.ShortenUrl.request_response.AuthResponse;
import com.elephants.ShortenUrl.request_response.ErrorRes;
import com.elephants.ShortenUrl.exception.UserAlreadyExistException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
@Slf4j
@Service
@AllArgsConstructor
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private JwtUtil jwtUtil;
    private CustomUserDetailsService userService;



    public ResponseEntity<?> loginInSystem(UserRequest userRequest) {
        try {
            Authentication authentication =
                    authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userRequest.getUsername(), userRequest.getPassword()));
            String username = authentication.getName();
            UserEntity user = new UserEntity(username, "");
            String token = jwtUtil.createToken(user);
            AuthResponse authResponse = new AuthResponse(username, token);
            log.info( username +" is login");
            return ResponseEntity.ok().header(HttpHeaders.AUTHORIZATION, token).body(authResponse);

        } catch (BadCredentialsException e) {
            ErrorRes errorResponse = new ErrorRes(HttpStatus.BAD_REQUEST, "Invalid username or password");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
        } catch (Exception e) {
            ErrorRes errorResponse = new ErrorRes(HttpStatus.BAD_REQUEST, e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
        }
    }

    public ResponseEntity<?> registerInSystem(UserRequest userRequest) {
        try {
            String username = userRequest.getUsername();
            userService.createUser(username, userRequest.getPassword());
            UserEntity user = new UserEntity(username, "");
            String token = jwtUtil.createToken(user);
            AuthResponse authResponse = new AuthResponse(username, token);
            log.info("Created new user "+ username);
            return ResponseEntity.ok().header(HttpHeaders.AUTHORIZATION, token).body(authResponse);

        } catch (UserAlreadyExistException e) {
            ErrorRes errorResponse = new ErrorRes(HttpStatus.BAD_REQUEST, "User already exist");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
        }
    }
}
