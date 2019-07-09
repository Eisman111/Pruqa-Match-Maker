package com.pruqa.matchmakerauth.service;

import com.pruqa.matchmakerauth.model.Role;
import com.pruqa.matchmakerauth.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("roleService")
public class RoleService {

    // == fields ==
    public RoleRepository roleRepository;

    // == constructor ==
    @Autowired
    public RoleService(final RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    // == init ==
    // This is necessary to create the default role and avoid errors on empty db
    public void createDefaultRole() {
        if (roleRepository.findByRole("USER") == null) {
            roleRepository.save(new Role(1,"USER"));
        }
    }
}
