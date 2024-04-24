package com.elephants.ShortenUrl.request_response;

import org.springframework.http.HttpStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class ErrorRes {
    HttpStatus httpStatus;
    String message;


}
