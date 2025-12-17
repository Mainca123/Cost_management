package com.example.HeThongQuanLyTaiChinhThongMinh.service.impl;

import com.example.HeThongQuanLyTaiChinhThongMinh.domain.dto.response.UserResponseDTO;
import com.example.HeThongQuanLyTaiChinhThongMinh.domain.entity.User;
import com.example.HeThongQuanLyTaiChinhThongMinh.domain.mapstruct.UserMapper;
import com.example.HeThongQuanLyTaiChinhThongMinh.repository.UserRepository;
import com.example.HeThongQuanLyTaiChinhThongMinh.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Override
    public User getUserByEmail(String email) {
        return userRepository.findByEmailAndDeletedAtIsNull(email).orElseThrow(()
        -> new RuntimeException("not.found.email."+email));
    }

    @Override
    public User getUserByUsername(String username) {
        return userRepository.findByUsernameAndDeletedAtIsNull(SecurityContextHolder.getContext().getAuthentication().getName())
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    @Override
    public UserResponseDTO getMe() {
        return userMapper.toUserResponseDTO(
                getUserByUsername(
                        SecurityContextHolder.getContext().getAuthentication().getName()
                ));
    }

    @Override
    public void save(User user) {
        userRepository.save(user);
    }

//    @Override
//    public String setUser(SetUserRequestDTO setUserRequestDTO) {
//        try {
//            User user = getUserByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
//            if(!user.getEmail().equals(setUserRequestDTO.getEmail())
//                    && userRepository.findByEmail(setUserRequestDTO.getEmail()).isPresent()){
//                throw new RuntimeException("same.email");
//            }
//            user.setFullName(setUserRequestDTO.getFullName());
//            user.setGender(setUserRequestDTO.getGender());
//            user.setEmail(setUserRequestDTO.getEmail());
//            user.setPhone(setUserRequestDTO.getPhone());
//
//            userRepository.save(user);
//            return "SUCCESS";
//        } catch (RuntimeException e) {
//            throw new RuntimeException(e.getMessage());
//        }
//    }
}
