package com.elephants.ShortenUrl.users;

import com.elephants.ShortenUrl.urls.UrlEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@Table(name = "users")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    public UserEntity(String username, String password, UserRole role) {
        this.username = username;
        this.password = password;
        this.role = role;
    }

    @Column(name = "username", nullable = false, length = 50)
    private String username;

    @Column(name = "password", nullable = false, length = 50)
    private String password;
    @Enumerated(EnumType.STRING)
    @Column(name = "role", nullable = false, length = 5)
    private UserRole role;

    public UserEntity(String username, String password) {
        this.username = username;
        this.password = password;
    }
//    @OneToMany(mappedBy="user",cascade = CascadeType.ALL)
//    private List<UrlEntity> urls;
}