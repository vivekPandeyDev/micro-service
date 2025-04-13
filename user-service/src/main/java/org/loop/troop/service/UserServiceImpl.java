package org.loop.troop.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.loop.troop.dto.RegisterDto;
import org.loop.troop.dto.UserDto;
import org.loop.troop.entity.User;
import org.loop.troop.event.UserEventService;
import org.loop.troop.exception.ResourceNotFoundException;
import org.loop.troop.repo.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final UserEventService userEventService;
    @Override
    public UserDto saveUser(RegisterDto registerDto) {
        User user = modelMapper.map(registerDto, User.class);
        User savedUser = userRepository.save(user);
        userEventService.createUser(savedUser.getId().toString());
        return modelMapper.map(savedUser, UserDto.class);
    }

    @Override
    public UserDto getUserByUsernameOrId(String username, UUID id) {
        User user = userRepository.findByUsernameOrId(username, id)
            .orElseThrow(() -> new ResourceNotFoundException("User", "username or ID", username != null ? username : id));
        return modelMapper.map(user, UserDto.class);
    }

    @Override
    public void deleteUserByUsernameOrId(String username, UUID id) {
        if (!userRepository.existsByUsernameOrId(username, id)) {
            throw new ResourceNotFoundException("User", "username or ID", username != null ? username : id);
        }
        userRepository.deleteByUsernameOrId(username, id);
    }

    @Override
    public boolean isUserExistByUsernameOrId(String username, UUID id) {
        return userRepository.existsByUsernameOrId(username, id);
    }
}
