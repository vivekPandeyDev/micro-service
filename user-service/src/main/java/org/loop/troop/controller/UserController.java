package org.loop.troop.controller;

import java.io.IOException;
import java.util.Map;

import org.loop.troop.dto.RegisterDto;
import org.loop.troop.dto.UserDto;
import org.loop.troop.event.UserEventService;
import org.loop.troop.response.ApiResponse;
import org.loop.troop.service.ImageService;
import org.loop.troop.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
@Slf4j
public class UserController {

    private final UserService userService;
    private final ImageService imageService;

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponse<UserDto> registerUser(
            @Valid @RequestPart("user") RegisterDto registerDto,
            @RequestPart(name = "image", required = false) MultipartFile image) throws IOException {

        // Check if user exists by username only (ID is null here)
        if (userService.isUserExistByUsernameOrId(registerDto.getUsername(), null)) {
            throw new org.loop.troop.exception.ServiceException(
                    "Username is already taken. Please choose another.",
                    HttpStatus.CONFLICT,
                    "User already exists");
        }

        // Save image if it is present
        if (image != null && !image.isEmpty()) {
            String savedImageName = imageService.save("profile-pics", image, registerDto.getUsername());
            registerDto.setProfileUrl(imageService.getImageUrl(savedImageName));
        }

        // Save user and return response
        UserDto createdUser = userService.saveUser(registerDto);
        return new ApiResponse<>(
                true,
                Map.of("user", createdUser),
                "User registered successfully!");
    }

}
