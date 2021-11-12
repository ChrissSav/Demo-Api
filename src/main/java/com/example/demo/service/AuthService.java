package com.example.demo.service;

import com.example.demo.dto.auth.AccessRefreshTokenResponse;
import com.example.demo.dto.auth.AuthenticationResponse;
import com.example.demo.dto.auth.LoginRequest;
import com.example.demo.dto.auth.RegisterRequest;
import com.example.demo.exception.ConflictException;
import com.example.demo.mapper.UserMapper;
import com.example.demo.model.User;
import com.example.demo.repository.UsersRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
@AllArgsConstructor
public class AuthService {

    private final UsersRepository usersRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final UserMapper userMapper;
    private final JwtProvider jwtProvider;


    public AuthenticationResponse register(RegisterRequest registerRequest) {

        Optional<User> userOptional = usersRepository.findByEmail(registerRequest.getEmail());

        if (userOptional.isPresent()) {
            throw new ConflictException("EMAIL_ALREADY_EXIST");
        }

        User user = new User();
        user.setEmail(registerRequest.getEmail());
        user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
        user.setName(registerRequest.getName());
        user.setEnable(true);
        user = usersRepository.save(user);

        return AuthenticationResponse.builder()
                .accessToken(jwtProvider.generateAccessToken(user.getEmail()))
                .refreshToken(jwtProvider.generateRefreshToken(user.getEmail()))
                .accountInfo(userMapper.mapToUserResponse(user))
                .build();
    }


    public AuthenticationResponse login(LoginRequest loginRequest) {

        try {

            Authentication authentication = authenticationManager.
                    authenticate(new UsernamePasswordAuthenticationToken(
                            loginRequest.getEmail(),
                            loginRequest.getPassword()));

            SecurityContextHolder.getContext().setAuthentication(authentication);
            User currentUser = getCurrentUser();
            return AuthenticationResponse.builder()
                    .accessToken(jwtProvider.generateAccessToken(currentUser.getEmail()))
                    .accountInfo(userMapper.mapToUserResponse(currentUser))
                    .refreshToken(jwtProvider.generateRefreshToken(currentUser.getEmail()))
                    .build();
        }catch (Exception e){
            e.printStackTrace();
            throw  e;
        }


    }


    public AccessRefreshTokenResponse refreshToken(String refreshToken) {
        try {
            String email = jwtProvider.verifyRefreshTokenReturnEmail(refreshToken);
            User user = usersRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("User name not found - " + email));
            AccessRefreshTokenResponse accessRefreshTokenResponse = new AccessRefreshTokenResponse();


            accessRefreshTokenResponse.setAccessToken(jwtProvider.generateAccessToken(user.getEmail()));
            accessRefreshTokenResponse.setRefreshToken(jwtProvider.generateRefreshToken(user.getEmail()));
            return accessRefreshTokenResponse;
        } catch (Exception exception) {
            throw new ConflictException("REFRESH_TOKEN_NOT_VALID");
        }

    }


    public User getCurrentUser() {
        return (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }
}