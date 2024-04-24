package com.elephants.ShortenUrl.request_response;

import com.elephants.ShortenUrl.urls.UrlEntity;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class UrlGetResponse {
    private Error error;

    private Long id;
    private String shortUrl;
    private String longUrl;
    private Long score;
    private Boolean isActive;

    public enum Error {
        OK,
        INVALID_URL_ID,
        NO_RIGHTS
    }

    public static UrlGetResponse success(UrlEntity url) {
        return builder().error(Error.OK)
                .id(url.getId())
                .shortUrl(url.getShortUrl())
                .longUrl(url.getLongUrl())
                .score(url.getScore())
                .isActive(url.getIsActive()).build();
    }

    public static UrlGetResponse failed(Error error) {
        return builder().error(error)
                .id(-1L)
                .shortUrl("Error")
                .longUrl("Error")
                .score(-1L)
                .isActive(false).build();
    }
}
