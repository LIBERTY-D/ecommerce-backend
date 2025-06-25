package com.daniel.apps.ecommerce.app.controller.imp;


import com.auth0.jwt.interfaces.DecodedJWT;
import com.daniel.apps.ecommerce.app.controller.Controller;
import com.daniel.apps.ecommerce.app.dto.user.PasswordDto;
import com.daniel.apps.ecommerce.app.dto.user.UserAdminDto;
import com.daniel.apps.ecommerce.app.dto.user.UserDto;
import com.daniel.apps.ecommerce.app.dto.user.UserResponseDto;
import com.daniel.apps.ecommerce.app.exception.AuthHeaderRequired;
import com.daniel.apps.ecommerce.app.http.HttpResponse;
import com.daniel.apps.ecommerce.app.model.User;
import com.daniel.apps.ecommerce.app.service.imp.UserServiceImp;
import com.daniel.apps.ecommerce.app.service.jwt.JwtService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Map;

@RestController
@RequiredArgsConstructor

public class UserController extends BaseController implements Controller<UserDto, UserResponseDto,Long> {
    
    private  final UserServiceImp userServiceImp;
    private  final JwtService jwtService;
    private  final ObjectMapper objectMapper;

    @GetMapping("users")
    
    @Override
    public ResponseEntity<HttpResponse<Collection<UserResponseDto>>> findAll() {
        Collection<UserResponseDto> users= userServiceImp.findAllUser();
        HttpResponse<Collection<UserResponseDto>> response =
                HttpResponse.<Collection<UserResponseDto>>builder()
                        .timeStamp(LocalDateTime.now())
                        .status(HttpStatus.OK)
                        .statusCode(HttpStatus.OK.value())
                        .message("Users retrieved successfully")
                        .data(users).length(users.size()).build();

        return ResponseEntity.ok(response);
    }

    @Override
    @GetMapping("users/{id}")
    public ResponseEntity<HttpResponse<UserResponseDto>> findOne(Long id) {

        HttpResponse<UserResponseDto> response =
                HttpResponse.<UserResponseDto>builder()
                        .timeStamp(LocalDateTime.now())
                        .status(HttpStatus.OK)
                        .statusCode(HttpStatus.OK.value())
                        .message("User retrieved successfully")
                        .data(userServiceImp.findUserByIdResponse(id)).build();
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("users/{id}")
    @Override
    public ResponseEntity<HttpResponse<UserResponseDto>> deleteOne(Long id) {
        userServiceImp.deleteUserById(id);
        HttpResponse<UserResponseDto> response =
                HttpResponse.<UserResponseDto>builder()
                        .timeStamp(LocalDateTime.now())
                        .status(HttpStatus.OK)
                        .statusCode(HttpStatus.OK.value())
                        .message("User deleted successfully").build();
        return ResponseEntity.ok(response);
    }

    @PatchMapping("users/{id}")
    @Override
    public ResponseEntity<HttpResponse<UserResponseDto>> updateOne(Long id, UserDto updatedEntity) {

        UserResponseDto user =  userServiceImp.updateUser(id,updatedEntity);
        HttpResponse<UserResponseDto> response =
                HttpResponse.<UserResponseDto>builder()
                        .timeStamp(LocalDateTime.now())
                        .status(HttpStatus.OK)
                        .statusCode(HttpStatus.OK.value()).data(user)
                        .message("User updated successfully").build();
        return ResponseEntity.ok(response);

    }

    @PatchMapping("users/password/{id}")
    public ResponseEntity<HttpResponse<UserResponseDto>> updateOne(@PathVariable Long id, @Valid @RequestBody PasswordDto passwordDto) {

        UserResponseDto user =  userServiceImp.updateUserPassword(id, passwordDto);
        HttpResponse<UserResponseDto> response =
                HttpResponse.<UserResponseDto>builder()
                        .timeStamp(LocalDateTime.now())
                        .status(HttpStatus.OK)
                        .statusCode(HttpStatus.OK.value()).data(user)
                        .message("password updated successfully").build();
        return ResponseEntity.ok(response);

    }


    @PatchMapping("users/admin/{id}")
    public ResponseEntity<HttpResponse<UserResponseDto>> updateUserAdmin(@PathVariable Long id,@Valid @RequestBody() UserAdminDto adminDto) {
        System.out.println(adminDto);
        UserResponseDto user =  userServiceImp.updateUserAdmin(id,adminDto);
        HttpResponse<UserResponseDto> response =
                HttpResponse.<UserResponseDto>builder()
                        .timeStamp(LocalDateTime.now())
                        .status(HttpStatus.OK)
                        .statusCode(HttpStatus.OK.value()).data(user)
                        .message("User updated successfully").build();
        return ResponseEntity.ok(response);

    }



    @PostMapping("users")
    @Override
    public ResponseEntity<HttpResponse<UserResponseDto>> createOne(UserDto newEntity) {
        ServletRequestAttributes attrs = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        UserResponseDto user =  userServiceImp.createUser(newEntity,attrs.getRequest());

        HttpResponse<UserResponseDto> response =
                HttpResponse.<UserResponseDto>builder()
                        .timeStamp(LocalDateTime.now())
                        .status(HttpStatus.OK)
                        .statusCode(HttpStatus.OK.value()).data(user)
                        .message("User created successfully").build();
        return ResponseEntity.ok(response);
    }

    @GetMapping("users/verify")
    public ResponseEntity<HttpResponse<UserResponseDto>> veryUser(@RequestParam(
            "token") String token) {
        UserResponseDto user =  userServiceImp.verifyUserByToken(token);
        HttpResponse<UserResponseDto> response =
                HttpResponse.<UserResponseDto>builder()
                        .timeStamp(LocalDateTime.now())
                        .status(HttpStatus.OK)
                        .statusCode(HttpStatus.OK.value())
                        .message("you are verified. You can now go and login").build();
        return ResponseEntity.ok(response);
    }



    @GetMapping("users/refresh/token")
    public void getRefreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException, AuthHeaderRequired {
        String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String refreshToken = authHeader.substring("Bearer ".length());
            try {
                DecodedJWT decodedJWT = jwtService.verifyToken(refreshToken);
                String email = decodedJWT.getSubject();
                User user = userServiceImp.findUserByEmail(email);
                HttpResponse<Object> httpResponse = HttpResponse.builder().build();
                httpResponse.setMessage("created token");
                httpResponse.setTimeStamp(LocalDateTime.now());
                httpResponse.setStatusCode(HttpStatus.OK.value());
                httpResponse.setStatus(HttpStatus.OK);

                String accessToken = jwtService.accessToken(user);
                httpResponse.setData(Map.of("access_token", accessToken,
                        "refresh_token", refreshToken));
                objectMapper.writeValue(response.getOutputStream(), httpResponse);
                response.setContentType(MediaType.APPLICATION_JSON_VALUE);

            } catch (Exception exp) {
                response.setStatus(HttpStatus.BAD_REQUEST.value());
                HttpResponse<Object> httpResponse =
                        HttpResponse.builder().build();
                httpResponse.setMessage(exp.getMessage());
                httpResponse.setTimeStamp(LocalDateTime.now());
                httpResponse.setStatusCode(HttpStatus.BAD_REQUEST.value());
                httpResponse.setStatus(HttpStatus.BAD_REQUEST);
                objectMapper.writeValue(response.getOutputStream(), httpResponse);
                response.setContentType(MediaType.APPLICATION_JSON_VALUE);
                response.getOutputStream().flush();
            }

        } else {
            throw new AuthHeaderRequired("missing Authorization header or " +
                    "Bearer ");

        }
    }

}
