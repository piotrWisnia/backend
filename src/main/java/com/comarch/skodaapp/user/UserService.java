package com.comarch.skodaapp.user;

import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserDto getUser(Long id) {
        return userRepository.findById(id)
                .map(user -> new UserDto(user.getEmail(), user.getName(), user.getLoyaltyPoints()))
                .orElseThrow(() -> new UserNotFoundException(id));
    }

    public Long addUser(CreateUserDto userDto) {
        var user = new User(userDto.email(), userDto.name(), userDto.password());
        return userRepository.save(user).getId();
    }
}
