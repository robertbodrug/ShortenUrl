package com.elephants.ShortenUrl.request_response;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class UrlDeleteResponse {
    private Error error;

    public enum Error {
        OK,
        INVALID_URL_ID,
        NO_RIGHTS
    }

    public static UrlDeleteResponse success() {
        return builder().error(Error.OK).build();
    }

    public static UrlDeleteResponse failed(Error error) {
        return builder().error(error).build();
    }
}
