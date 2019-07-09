package com.pruqa.matchmakerauth.controller;

import com.pruqa.matchmakerauth.model.Company;
import com.pruqa.matchmakerauth.service.CompanyService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

import java.security.Principal;
import java.util.LinkedHashMap;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/api/v1/")
@ApiIgnore(value = "Internal API for OAuth2")
public class UserController {

    // == fields ==
    private CompanyService companyService;

    // == constructor ==
    @Autowired
    public UserController(final CompanyService companyService) {
        this.companyService = companyService;
    }

    // == handlers requests ==
    // ==== USER ====
    @RequestMapping(value = { "/me" }, method = RequestMethod.GET)
    public Map<String, String> simpleUserId(final Principal principal) {
        final Company company = companyService.findCompanyByEncryptedEmail(principal.getName());
        Map<String, String> map = new LinkedHashMap<>();
        map.put("name", company.getCompanyName());
        return map;
    }
}
