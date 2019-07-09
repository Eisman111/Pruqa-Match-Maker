package com.pruqa.matchmakerauth.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import springfox.documentation.annotations.ApiIgnore;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.util.Set;

@Data
@Entity
@Table(name = "Company", indexes = {@Index(name = "email",  columnList="email", unique = true)})
@ApiModel(value = "Company")
public class Company {

    // == fields ==
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "COMPANY_ID")
    @ApiModelProperty(notes = "Id of the company is necessary to identify your settings")
    private int companyId;

    @Column(name = "NAME")
    @NotBlank(message = "Company name is required")
    @ApiModelProperty(notes = "Name of the company is necessary to identify your settings")
    private String companyName;

    @Column(name = "EMAIL")
    @NotBlank(message = "Company email is required")
    @ApiModelProperty(notes = "Email of the company is necessary to login to the service")
    private String companyEmail;

    @Column(name = "PASSWORD")
    @NotBlank(message = "Company password is required")
    @ApiModelProperty(notes = "Password of the company is necessary to login to the service")
    private String password;

    @Column(name = "ACTIVE")
    @Min(value = 0)
    @Max(value = 1)
    @ApiModelProperty(notes = "Activation status of the company is necessary to login to the service")
    private int active;

    @ManyToMany
    @JoinTable(name = "USER_ROLE",
            joinColumns = @JoinColumn(name = "COMPANY_ID"),
            inverseJoinColumns = @JoinColumn(name = "ROLE_ID"))
    @JsonIgnore
    private Set<Role> roles;
}