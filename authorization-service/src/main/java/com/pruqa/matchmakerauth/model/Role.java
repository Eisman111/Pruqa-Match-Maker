package com.pruqa.matchmakerauth.model;

import lombok.Data;
import springfox.documentation.annotations.ApiIgnore;

import javax.persistence.*;

@Data
@Entity
@Table(name = "Role")
public class Role {

    // == fields ==
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @Column(name="ROLE_ID")
    private int id;

    @Column(name="ROLE")
    private String role;

    // == constructor ==
    public Role() {
    }

    public Role (int id, String role) {
        this.id = id;
        this.role = role;
    }
}