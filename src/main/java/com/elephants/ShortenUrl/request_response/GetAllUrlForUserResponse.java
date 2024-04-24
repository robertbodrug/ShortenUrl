package com.elephants.ShortenUrl.request_response;

import com.elephants.ShortenUrl.urls.UrlEntity;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Builder
@Data
public class GetAllUrlForUserResponse {
    private Error error;

    private String username;
    private List<UrlEntity> allUrlForUser;

    public enum Error {
        OK,
        INVALID_USERNAME,
        NO_RIGHTS
    }

    public static GetAllUrlForUserResponse success(String username,List<UrlEntity> allUrlForUser) {
        return builder().username(username).error(Error.OK).allUrlForUser(allUrlForUser).build();
    }

    public static GetAllUrlForUserResponse failed(Error error) {

        return builder().error(error).allUrlForUser(null).build();
    }
}
