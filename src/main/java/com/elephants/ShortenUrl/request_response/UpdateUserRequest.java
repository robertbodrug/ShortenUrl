package com.elephants.ShortenUrl.request_response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class UpdateUserRequest {
    private Long id;
    private String username;
    private String password;
}
