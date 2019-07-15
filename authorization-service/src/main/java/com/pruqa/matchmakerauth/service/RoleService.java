package com.pruqa.matchmakerauth.service;

import com.pruqa.matchmakerauth.model.Role;
import com.pruqa.matchmakerauth.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("roleService")
public class RoleService {

    // == fields ==
    private RoleRepository roleRepository;

    // == constructor ==
    @Autowired
    public RoleService(final RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    // == init ==
    /**
     * Create default role
     * NOTE: This method is necessary for new dbs
     */
    public void createDefaultRole() {
        if (roleRepository.findByRole("USER") == null) {
            roleRepository.save(new Role(1,"USER"));
        }
    }
}
