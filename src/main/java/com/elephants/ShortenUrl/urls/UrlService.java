package com.elephants.ShortenUrl.urls;


import com.elephants.ShortenUrl.request_response.*;

import com.elephants.ShortenUrl.users.CustomUserDetailsService;
import com.elephants.ShortenUrl.users.UserEntity;
import com.elephants.ShortenUrl.users.UserRepository;
import com.elephants.ShortenUrl.users.UserRole;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Optional;
import java.util.Random;
@Slf4j
@Service
@RequiredArgsConstructor
public class UrlService {
    private final UserRepository userRepository;
    private final UrlRepository repository;

    public UrlCreateResponse create(String username, UrlCreateRequest request) {
        Optional<UrlCreateResponse.Error> validationError = validateCreateFields(request);

        if (validationError.isPresent()) {
            return UrlCreateResponse.failed(validationError.get());
        }

        UrlEntity createdUrl = repository.save(UrlEntity.builder()
                .userId(userRepository.findIdByUsername(username))
                .shortUrl(generateShortUrl())
                .longUrl(request.getLongUrl())
                .score(0L)
                .isActive(true)
                .build());
        log.info(createdUrl.getLongUrl()+" is created");
        return UrlCreateResponse.success(createdUrl.getId(), createdUrl.getShortUrl());
    }

    public UrlGetResponse get(String username, Long id) {
        Optional<UrlEntity> optionalUrl = repository.findById(id);

        if (optionalUrl.isEmpty()) {
            return UrlGetResponse.failed(UrlGetResponse.Error.INVALID_URL_ID);
        }
        UrlEntity url = optionalUrl.get();
        boolean isNotUserUrl = isNotUserUrl(username, url);

        if (isNotUserUrl) {
            return UrlGetResponse.failed(UrlGetResponse.Error.NO_RIGHTS);
        }



        repository.findById(url.getId());
        log.info(url.getShortUrl()+" is find");
        return UrlGetResponse.success(url);
    }

    public UrlUpdateResponse update(String username, UrlUpdateRequest request) {
        Optional<UrlEntity> optionalNote = repository.findById(request.getId());

        if (optionalNote.isEmpty()) {
            return UrlUpdateResponse.failed(UrlUpdateResponse.Error.INVALID_URL_ID);
        }

        UrlEntity url = optionalNote.get();

        if (isNotUserUrl(username, url)) {
            return UrlUpdateResponse.failed(UrlUpdateResponse.Error.NO_RIGHTS);
        }



        url.setShortUrl(request.getShortUrl());
        url.setIsActive(true);

        repository.save(url);

        return UrlUpdateResponse.success(url);
    }

    public UrlDeleteResponse delete(String username, long id) {
        Optional<UrlEntity> optionalNote = repository.findById(id);

        if (optionalNote.isEmpty()) {
            return UrlDeleteResponse.failed(UrlDeleteResponse.Error.INVALID_URL_ID);
        }

        UrlEntity url = optionalNote.get();

        boolean isNotUserUrl = isNotUserUrl(username, url);

        if (isNotUserUrl) {
            return UrlDeleteResponse.failed(UrlDeleteResponse.Error.NO_RIGHTS);
        }

        repository.delete(url);

        return UrlDeleteResponse.success();
    }

    public String getLongUrl(String shortUrl) {

        UrlEntity url = repository.findByShortUrl(shortUrl);
        url.setScore(url.getScore() + 1);
        repository.save(url);

        return url.getLongUrl();
    }

    public String generateShortUrl() {
        int length = new Random().nextInt(3) + 6; // Generate length between 6 and 8
        String characters = "abcdefghijklmnopqrstuvwxyz0123456789";
        StringBuilder shortLink = new StringBuilder();
        for (int i = 0; i < length; i++) {
            int index = new Random().nextInt(characters.length());
            shortLink.append(characters.charAt(index));
        }
        return shortLink.toString();
    }

    public Optional<UrlCreateResponse.Error> validateCreateFields(UrlCreateRequest request) {
        try {
            URL link = new URL(request.getLongUrl());
            HttpURLConnection connection = (HttpURLConnection) link.openConnection();
            connection.setRequestMethod("GET");
            connection.setConnectTimeout(5000);
            int responseCode = connection.getResponseCode();
            if(!(responseCode >= 200 && responseCode < 300)){
                return Optional.of(UrlCreateResponse.Error.INVALID_LONG_URL);
            }
        } catch (IOException e) {
            return Optional.of(UrlCreateResponse.Error.INVALID_LONG_URL);
        }
        if (Objects.isNull(request.getLongUrl())) {
            return Optional.of(UrlCreateResponse.Error.INVALID_LONG_URL);
        }
        return Optional.empty();
    }


    private boolean isNotUserUrl(String username, UrlEntity url) {
        return !Objects.equals(url.getUserId(), userRepository.findIdByUsername(username));
    }
}
