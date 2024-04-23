package com.elephants.ShortenUrl.urls;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UrlRepository extends JpaRepository<UrlEntity, Long> {

    @Query(nativeQuery = true, value = "SELECT * FROM urls WHERE short_url = :shortUrl")
    UrlEntity findByShortUrl(String shortUrl);
}
