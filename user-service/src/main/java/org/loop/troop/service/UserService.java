package org.loop.troop.service;

import java.util.UUID;

import org.loop.troop.dto.RegisterDto;
import org.loop.troop.dto.UserDto;

public interface UserService {
    UserDto saveUser(RegisterDto registerDto);
    UserDto getUserByUsernameOrId(String username,UUID id);
    void deleteUserByUsernameOrId(String username,UUID id);
    boolean isUserExistByUsernameOrId(String username,UUID id);
}
