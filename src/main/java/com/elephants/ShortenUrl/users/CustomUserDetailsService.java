package com.elephants.ShortenUrl.users;


import com.elephants.ShortenUrl.exception.UserAlreadyExistException;
import com.elephants.ShortenUrl.request_response.*;
import com.elephants.ShortenUrl.urls.UrlEntity;
import com.elephants.ShortenUrl.urls.UrlRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@AllArgsConstructor
@Service
public class CustomUserDetailsService implements UserDetailsService {
    private final PasswordEncoder encoder;
    private final UserRepository userRepository;
    private final UrlRepository urlRepository;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity user = userRepository.findByUsername(username);


        return org.springframework.security.core.userdetails.User.builder()
                        .username(user.getUsername())
                        .password(user.getPassword())
                        .roles(String.valueOf(user.getRole()))
                        .build();
    }



    @Transactional
    public void createUser(String username,
                           String password) throws UserAlreadyExistException {
        if (userRepository.existsByUsername(username)) {
            throw new UserAlreadyExistException(username);
        }
        UserEntity user = new UserEntity(username, encoder.encode(password),UserRole.USER);
        userRepository.save(user);
    }

        public GetAllUrlForUserResponse getAllUrlForUserResponse(String holdername,String username){

        if(isNotUserUser(holdername,username)){
                return GetAllUrlForUserResponse.failed(GetAllUrlForUserResponse.Error.NO_RIGHTS);
            }
        List<UrlEntity> urls = urlRepository.findAllByUsername(username);
        return GetAllUrlForUserResponse.success(username,urls);

    }

    public UpdateUserResponse updateUser(String holdername,UpdateUserRequest request){

        Optional<UserEntity> optionalUserEntity = userRepository.findById(request.getId());

        if(optionalUserEntity.isEmpty()){
            return UpdateUserResponse.failed(UpdateUserResponse.Error.invalidUsername);
        }
        UserEntity userEntity = optionalUserEntity.get();

        if(isNotUserUser(holdername,userEntity.getUsername())){
            return UpdateUserResponse.failed(UpdateUserResponse.Error.NO_RIGHTS);
        }

        UserEntity updatedEntity = userRepository.save(UserEntity.builder()
                .id(request.getId())
                .username(userEntity.getUsername())
                .password(userEntity.getPassword())
                .role(userEntity.getRole())
                .build());

        userRepository.save(updatedEntity);

        return UpdateUserResponse.success(updatedEntity.getUsername());
    }

    public ErrorRes deleteUser(String holdername,String username){

        UserEntity user = userRepository.findByUsername(username);
        if(isNotUserUser(holdername,username)){
            return new ErrorRes(HttpStatus.FORBIDDEN,"No rights");
        }
        if(user==null){
            return new ErrorRes(HttpStatus.NOT_FOUND,"User not found");
        }
        userRepository.delete(user);

        return new ErrorRes(HttpStatus.OK,username+" is deleted");

    }

    public List<UserEntity> getAllUsers() {
        return userRepository.findAll();
    }

    private boolean isNotUserUser(String holder, String request) {
        return !Objects.equals(holder, request);
    }
}
