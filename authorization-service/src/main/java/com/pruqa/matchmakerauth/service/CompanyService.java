/*
 * Piggus - Copyright (c) 2018 by Paolo Amosso
 * License: GNU Affero General Public License v3.0
 */

/*
 * This is the bridge between the repository and the controller
 */

package com.pruqa.matchmakerauth.service;

import com.pruqa.matchmakerauth.model.Company;
import com.pruqa.matchmakerauth.model.Role;
import com.pruqa.matchmakerauth.repository.CompanyRepository;
import com.pruqa.matchmakerauth.repository.RoleRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.encrypt.TextEncryptor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

@Slf4j
@Service("userService")
public class CompanyService {

    // == fields ==
    private CompanyRepository companyRepository;
    private RoleRepository roleRepository;
    private PasswordEncoder bCryptPasswordEncoder;
    private TextEncryptor eTextEncryptor;

    // == constructor ==
    @Autowired
    public CompanyService (final CompanyRepository companyRepository,
                           final RoleRepository roleRepository,
                           final PasswordEncoder passwordEncoder,
                           final TextEncryptor eTextEncryptor) {
        this.companyRepository = companyRepository;
        this.roleRepository = roleRepository;
        this.bCryptPasswordEncoder = passwordEncoder;
        this.eTextEncryptor = eTextEncryptor;

    }

    // == public methods ==
    public Company findCompanyByEncryptedEmail(final String email) {
        final Company company = companyRepository.findByCompanyEmail(email);
        return decriptCompany(company);
    }

    public Company findCompanyByDecryptedEmail(final String email) {
        List<Company> companies = companyRepository.findByActive(1);
        for (Company u : companies) {
            String emailDecrypted = eTextEncryptor.decrypt(u.getCompanyEmail());
            if (emailDecrypted.equals(email)) {
                return u;
            }
        }
        return null;
    }

    public void createUser(final Company company) {
        company.setPassword(bCryptPasswordEncoder.encode(company.getPassword()));
        company.setActive(1);
        company.setCompanyName(eTextEncryptor.encrypt(company.getCompanyName()));
        company.setCompanyEmail(eTextEncryptor.encrypt(company.getCompanyEmail()));
        Role companyRole = roleRepository.findByRole("USER");
        company.setRoles(new HashSet<>(Arrays.asList(companyRole)));
        companyRepository.save(company);
    }

    private Company decriptCompany(final Company company) {
        Company decriptedCompany = new Company();
        BeanUtils.copyProperties(company,decriptedCompany);
        decriptedCompany.setCompanyName(eTextEncryptor.decrypt(decriptedCompany.getCompanyName()));
        return decriptedCompany;
    }
}
