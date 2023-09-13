package com.example.bookstore.service.impls;

import com.example.bookstore.repository.UserRepository;
import com.example.bookstore.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    @Override
    public void delete(Long id) {
        userRepository.deleteById(id);
    }
}
