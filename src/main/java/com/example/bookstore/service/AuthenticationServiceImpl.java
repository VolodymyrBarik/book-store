package com.example.bookstore.service;

import com.example.bookstore.dto.UserRegistrationRequestDto;
import com.example.bookstore.dto.UserResponseDto;
import com.example.bookstore.exception.RegistrationException;
import com.example.bookstore.mapper.UserMapper;
import com.example.bookstore.model.Role;
import com.example.bookstore.model.User;
import com.example.bookstore.repository.UserRepository;
import java.util.HashSet;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {
    private final PasswordEncoder passwordEncoder;
    private final UserDetailsService userDetailsService;
    private final UserMapper userMapper;
    private final UserRepository userRepository;
    private final RoleService roleService;

    //    @Override
    //    public UserLoginResponseDto login(UserLoginRequestDto request) {
    //        UserDetails userDetails = userDetailsService.loadUserByUsername(request.getEmail());
    //        if (userDetails.getPassword().equals(passwordEncoder.encode(request.getPassword()))) {
    //            return userMapper.toUserLoginResponseDto((User) userDetails);
    //        }
    //        throw new EntityNotFoundException("Login or password incorrect ");
    //    }

    @Override
    public UserResponseDto register(UserRegistrationRequestDto requestDto)
            throws RegistrationException {
        if (userRepository.findByEmail(requestDto.getEmail()).isPresent()) {
            throw new RegistrationException("Login is already taken, try another one");
        }
        User user = new User();
        user.setEmail(requestDto.getEmail());
        user.setPassword(passwordEncoder.encode(requestDto.getPassword()));
        Set<Role> roleSet = new HashSet<>();
        Role userRole = roleService.getByName(Role.RoleName.USER.name());
        roleSet.add(userRole);
        user.setRoles(roleSet);
        user.setFirstName(requestDto.getFirstName());
        user.setLastName(requestDto.getLastName());
        user.setShippingAddress(requestDto.getShippingAddress());
        User userFromDb = userRepository.save(user);
        return userMapper.toResponseDto(userFromDb);
    }
}
