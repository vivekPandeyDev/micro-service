package org.loop.troop.dto;

import org.loop.troop.validator.ValidEnum;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class RegisterDto {

    @NotBlank(message = "{username.notblank}")
    @Pattern(regexp = "^([a-z]{5,20}|[a-z]{5,20}(_[a-z]{1,20})?)$", message = "{username.pattern}")
    private String username;

    @NotBlank(message = "{firstName.notblank}")
    private String firstName;

    @NotBlank(message = "{lastName.notblank}")
    private String lastName;

    @Email(message = "{email.invalid}")
    @NotBlank(message = "{email.notblank}")
    private String email;

    @NotBlank(message = "{password.notblank}")
    private String password;

    private boolean enable = true;

    private boolean emailVerified = false;

    private String profileUrl;

    @ValidEnum(enumClass = Gender.class, message = "{gender.invalid}")
    private String gender;
}