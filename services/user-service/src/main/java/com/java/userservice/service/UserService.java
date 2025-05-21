package com.java.userservice.service;

import com.java.userservice.domain.Role;
import com.java.userservice.domain.User;
import com.java.userservice.domain.UserCreatedEvent;
import com.java.userservice.dto.request.UserCreationDto;
import com.java.userservice.enums.Erole;
import com.java.userservice.repository.RoleRepository;
import com.java.userservice.repository.UserRepository;
import com.java.userservice.utils.UserMapper;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class UserService {
    private final Logger logger = LoggerFactory.getLogger(getClass());

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final UserMapper userMapper;

    public UserService(UserRepository userRepository, RoleRepository roleRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.userMapper = userMapper;
    }

    @Transactional
    public User createUser(UserCreationDto userCreationDto) {
        Set<Erole> eRoleList = userCreationDto.getEroleList();
        eRoleList.add(Erole.END_USER);                          // default is end_user
        List<Role> roles = eRoleList.stream()
                .map(erole -> roleRepository.findRoleByErole(erole)).flatMap(Optional::stream).toList();

        User user = userMapper.toEntity(userCreationDto.getUsername(), userCreationDto.getEmail(), roles, userCreationDto.getPassword(), true);
        // todo: need to encode password
        User savedUser = userRepository.save(user);

        UserCreatedEvent userCreatedEvent = new UserCreatedEvent(savedUser.getId(), savedUser.getUsername(), savedUser.getEmail());
//        userEventPublisher.publish(savedUser, userCreatedEvent);

        return savedUser;
    }

    public User findById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(String.format("User with id %s not found", id)));
    }

    public List<User> findALl() {
        return (List<User>) userRepository.findAll();
    }
}
