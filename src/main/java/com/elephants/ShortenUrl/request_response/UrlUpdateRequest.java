package com.elephants.ShortenUrl.request_response;

import lombok.Data;

@Data
public class UrlUpdateRequest {
    private long id;
    private String shortUrl;
}
