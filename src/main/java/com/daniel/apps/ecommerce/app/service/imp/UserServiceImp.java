package com.daniel.apps.ecommerce.app.service.imp;


import com.auth0.jwt.interfaces.DecodedJWT;
import com.daniel.apps.ecommerce.app.dto.user.PasswordDto;
import com.daniel.apps.ecommerce.app.dto.user.UserAdminDto;
import com.daniel.apps.ecommerce.app.dto.user.UserDto;
import com.daniel.apps.ecommerce.app.dto.user.UserResponseDto;
import com.daniel.apps.ecommerce.app.environment.MailEnv;
import com.daniel.apps.ecommerce.app.exception.NoSuchToken;
import com.daniel.apps.ecommerce.app.exception.NoSuchUserException;
import com.daniel.apps.ecommerce.app.mapper.UserMapper;
import com.daniel.apps.ecommerce.app.model.User;
import com.daniel.apps.ecommerce.app.model.enums.Role;
import com.daniel.apps.ecommerce.app.repository.UserRepository;
import com.daniel.apps.ecommerce.app.service.email.EmailService;
import com.daniel.apps.ecommerce.app.service.jwt.JwtService;
import com.daniel.apps.ecommerce.app.util.TokenUtil;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserServiceImp {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private final EmailService emailService;
    private final MailEnv mailEnv;
    private final JwtService jwtService;

    @Transactional
    public Collection<UserResponseDto> findAllUser() {

        var users = this.userRepository.findUsersWithAddress();
        return users.stream().map(userMapper::touserResponseDto).collect(Collectors.toList());
    }

    @Transactional
    public UserResponseDto findUserByIdResponse(Long id) {

        User user = this.findById(id);

        return userMapper.touserResponseDto(user);

    }

    public User findUserById(Long id) {
        return this.findById(id);


    }


    public void deleteUserById(Long id) {
        User user = findById(id);
        userRepository.delete(user);

    }

    public UserResponseDto createUser(UserDto userDto, HttpServletRequest request) {
        User user = new User();
        user.setEmail(userDto.getEmail());
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        user.setLastName(userDto.getLastName());
        user.setFirstName(userDto.getFirstName());
        String token = TokenUtil.confirmationToken();
        user.setCreateAccountToken(token);

        user.setEnabled(ifAdminMakesRequest(request));
        user.setRoles(Set.of(Role.ROLE_USER));

        User createdUser = userRepository.save(user);
        //  send email
//        emailService.sendEmail(MailUtil.sendCreateAccountEmailPayload(userDto.getEmail(),userDto.getFirstName(),mailEnv.getVerifyEmailUrl()+"?token="+token),"create-account-email.html");
        return userMapper.touserResponseDto(createdUser);

    }

    private boolean ifAdminMakesRequest(HttpServletRequest request) {
        try {
            String token = request.getHeader(HttpHeaders.AUTHORIZATION).substring("Bearer ".length());
            DecodedJWT decodedJWT = jwtService.verifyToken(token);
            String email = decodedJWT.getSubject();
            Optional<User> user = userRepository.findUserByEmail(email);
            if (user.isPresent()) {
                User foundUser = user.get();
                return foundUser.getRoles().stream().anyMatch(role -> role.name().equals("ROLE_ADMIN"));

            }
        } catch (Exception e) {
            return false;
        }
        return false;
    }

    public UserResponseDto updateUser(Long id, UserDto userDto) {
        User user = findById(id);
        user.setEmail(userDto.getEmail());
        user.setLastName(userDto.getLastName());
        user.setFirstName(userDto.getFirstName());
        if (userDto.getPhoneNumber() != null && !userDto.getPhoneNumber().isEmpty()) {
            user.setPhoneNumber(user.getPhoneNumber());
        }
        User updatedUser = userRepository.save(user);
        return userMapper.touserResponseDto(updatedUser);

    }

    public UserResponseDto updateUserAdmin(Long id, UserAdminDto userAdminDto) {
        User user = findById(id);
        user.setEmail(userAdminDto.getEmail());
        user.setLastName(userAdminDto.getLastName());
        user.setFirstName(userAdminDto.getFirstName());
        Set<Role> roles = user.getRoles();
        roles.add(userAdminDto.getRole());
        user.setRoles(roles);
        user.setEnabled(userAdminDto.isEnabled());
        user.setNonLocked(userAdminDto.isNonLocked());
        user.setPassword(passwordEncoder.encode(userAdminDto.getPassword()));
        User updatedUser = userRepository.save(user);
        return userMapper.touserResponseDto(updatedUser);

    }

    public User findUserByEmail(String email) {
        Optional<User> optionalUser = userRepository.findUserByEmail(email);
        if (optionalUser.isEmpty()) {
            throw new NoSuchUserException("no such user");
        }

        return optionalUser.get();

    }

    private User findById(Long id) {
        Optional<User> userOpt = userRepository.findUserWithAddressId(id);
        if (userOpt.isEmpty()) {
            throw new NoSuchUserException("no user with such id");
        }
        return userOpt.get();
    }


    public UserResponseDto verifyUserByToken(String token) {
        if (token.isEmpty()) {
            throw new NoSuchToken("Token not found");
        }
        Optional<User> user = userRepository.findUserByCreateAccountToken(token);
        if (user.isEmpty()) {
            throw new NoSuchUserException("Could not verify your account.");
        }
        User updateUser = user.get();
        updateUser.setCreateAccountToken("");
        updateUser.setEnabled(true);
        User updatedUser = userRepository.save(updateUser);
        return userMapper.touserResponseDto(updatedUser);
    }

    public UserResponseDto updateUserPassword(Long id, PasswordDto passwordDto) {
        User user = findById(id);
        user.setPassword(passwordEncoder.encode(passwordDto.getPassword()));
        User updatedUser = userRepository.save(user);
        return userMapper.touserResponseDto(updatedUser);
    }
}
