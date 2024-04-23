package com.elephants.ShortenUrl.urls.request_response;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class UrlCreateResponse {
    private Error error;
    private Long id;
    private String createdShortUrl;

    public enum Error {
        OK,
        INVALID_LONG_URL
    }

    public static UrlCreateResponse success(Long id,String createdShortUrl) {
        return builder().error(Error.OK).id(id).createdShortUrl(createdShortUrl).build();
    }

    public static UrlCreateResponse failed(Error error) {
        return builder().error(error).createdShortUrl("false").build();
    }
}
