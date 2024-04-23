package com.elephants.ShortenUrl.urls.request_response;

import lombok.Data;

@Data
public class UrlUpdateRequest {
    private long id;
    private String shortUrl;
}
