package com.pruqa.matchmakerauth.service;

import com.pruqa.matchmakerauth.model.Company;
import com.pruqa.matchmakerauth.repository.CompanyRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.encrypt.TextEncryptor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service("userDetailsService")
public class CustomUserDetailService implements UserDetailsService {

    private CompanyRepository companyRepository;
    private TextEncryptor eTextEncryptor;

    // == constructor ==
    @Autowired
    public CustomUserDetailService(final CompanyRepository companyRepository, final TextEncryptor eTextEncryptor) {
        this.companyRepository = companyRepository;
        this.eTextEncryptor = eTextEncryptor;
    }

    @Override
    public UserDetails loadUserByUsername(final String username) throws UsernameNotFoundException {
        List<Company> companies = companyRepository.findByActive(1);
        User user = null;
        for (Company u : companies) {
            if (eTextEncryptor.decrypt(u.getCompanyEmail()).equals(username)) {
                user = buildUserFromUserEntity(u);
            }
        }
        if (user == null) {
            throw new UsernameNotFoundException("Email not found");
        }
        return user;
    }

    private User buildUserFromUserEntity(final Company company) {
        // convert model user to spring security user
        String username = company.getCompanyEmail();
        String password = company.getPassword();
        boolean enabled = true;
        boolean accountNonExpired = true;
        boolean credentialsNonExpired = true;
        boolean accountNonLocked = true;
        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority("USER"));

        User springUser = new User(username, password, enabled,
                accountNonExpired, credentialsNonExpired, accountNonLocked,
                authorities);
        return springUser;
    }
}
