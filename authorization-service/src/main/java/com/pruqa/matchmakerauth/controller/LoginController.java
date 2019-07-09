package com.pruqa.matchmakerauth.controller;

import com.pruqa.matchmakerauth.model.Company;
import com.pruqa.matchmakerauth.service.CompanyService;
import com.pruqa.matchmakerauth.service.RoleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import javax.validation.Valid;

@Slf4j
@RestController
@Api(value = "security-controller")
public class LoginController {

    // == fields ==
    private CompanyService companyService;
    private RoleService roleService;

    // == constructor ==
    @Autowired
    public LoginController(CompanyService companyService, RoleService roleService) {
        this.companyService = companyService;
        this.roleService = roleService;
    }

    // == init ==
    @PostConstruct
    public void createUser () {
        log.info("started the creation of the user");
        roleService.createDefaultRole();
    }

    // == handlers requests ==
    @ApiOperation(value = "Enable company to use the service",
            notes = "This operation can be used to add a company to the match making service")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "The service received correctly the request"),
            @ApiResponse(code = 401, message = "Unauthorized access, missing credentials"),
            @ApiResponse(code = 403, message = "Authorization failed, wrong credentials")
    })
    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public ResponseEntity<String> createNewUser(@Valid @RequestBody Company company) {
        Company checkedCompany = companyService.findCompanyByDecryptedEmail(company.getCompanyEmail());
        if (checkedCompany != null) {
            return ResponseEntity.badRequest().build();
        } else {
            companyService.createUser(company);
        }
        return ResponseEntity.ok().build();
    }
}
