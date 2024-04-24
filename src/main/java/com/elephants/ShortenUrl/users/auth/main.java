package com.elephants.ShortenUrl.users.auth;

import com.elephants.ShortenUrl.users.UserEntity;
import com.elephants.ShortenUrl.users.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
@AllArgsConstructor
@Service
public class main {

    static UserRepository userRepository ;
    public static void main(String[] args) {

        UserEntity admin1 = userRepository.findByUsername("admin1");
        System.out.println(admin1.getUsername());
        System.out.println(admin1.getId());
        EncoderConfig encoderConfig = new EncoderConfig();
        System.out.println(encoderConfig.passwordEncoder().encode("admin1"));
    }
}
