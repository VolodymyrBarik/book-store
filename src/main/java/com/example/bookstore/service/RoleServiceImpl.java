package com.example.bookstore.service;

import com.example.bookstore.model.Role;
import com.example.bookstore.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class RoleServiceImpl implements RoleService {
    private final RoleRepository roleRepository;

    @Override
    public Role getByName(String name) {
        return roleRepository.findByRoleName(Role.RoleName.valueOf(name));
    }
}
