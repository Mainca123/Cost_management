package com.example.HeThongQuanLyTaiChinhThongMinh.service.impl;

import com.example.HeThongQuanLyTaiChinhThongMinh.domain.dto.request.LoginRequestDTO;
import com.example.HeThongQuanLyTaiChinhThongMinh.domain.dto.request.RegisterRequestDTO;
import com.example.HeThongQuanLyTaiChinhThongMinh.domain.dto.response.LoginResponseDTO;
import com.example.HeThongQuanLyTaiChinhThongMinh.domain.entity.User;
import com.example.HeThongQuanLyTaiChinhThongMinh.repository.UserRepository;
import com.example.HeThongQuanLyTaiChinhThongMinh.service.AuthService;
import com.example.HeThongQuanLyTaiChinhThongMinh.service.TokenService;
import com.example.HeThongQuanLyTaiChinhThongMinh.service.UserService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;


@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserService userService;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenService tokenService;

    @Override
    public LoginResponseDTO login(LoginRequestDTO loginRequestDTO) {
        User user = userService.getUserByEmail(loginRequestDTO.getEmail());
        if(!passwordEncoder.matches(loginRequestDTO.getPassword(), user.getPassword()))
            throw new RuntimeException("invalid.information.login");
        return LoginResponseDTO.builder().token(
                tokenService.generateAccessToken(user.getUsername(), String.valueOf(user.getId())))
                .build();
    }

    @Override
    @Transactional
    public String register(RegisterRequestDTO registerRequestDTO) {
        try {
            String username =
                    java.text.Normalizer.normalize(registerRequestDTO.getFullName(), java.text.Normalizer.Form.NFD)
                            .replaceAll("\\p{M}+", "").toLowerCase().trim()
                            .replaceAll("[^a-z0-9 ]", "").replaceAll("\\s+", ".");
            username += (userRepository.count() + 1);
            User user = User.builder()
                    .fullName(registerRequestDTO.getFullName())
                    .email(registerRequestDTO.getEmail())
                    .username(username)
                    .password(passwordEncoder.encode(registerRequestDTO.getPassword()))
                    .createdAt(LocalDateTime.now())
                    .role(2).build();
            if(userRepository.findByEmailAndDeletedAtIsNull(registerRequestDTO.getEmail()).isPresent()){
                throw new RuntimeException("same.email");
            }
            userService.save(user);
            return "SUCCESS";
        } catch (RuntimeException e){
            throw new RuntimeException(e.getMessage());
        }
    }
}
