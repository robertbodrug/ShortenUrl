package com.elephants.ShortenUrl.urls;

import com.elephants.ShortenUrl.users.UserEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "urls")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UrlEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "short_url", nullable = false)
    private String shortUrl;

    @Column(name = "long_url", nullable = false)
    private String longUrl;

    @Column(name = "score", nullable = false)
    private Long score;

    @Column(name = "is_active", nullable = false)
    private Boolean isActive;

//    @JsonIgnore
//    @ManyToOne
//    @JoinColumn(name="user_id", referencedColumnName = "id")
//    private UserEntity user;
    @Column(name = "user_id", nullable = false)
    private Long userId;


}