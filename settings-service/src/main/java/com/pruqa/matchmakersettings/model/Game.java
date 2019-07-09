package com.pruqa.matchmakersettings.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.List;

@Data
@Entity
@Table(indexes = {
        @Index(name = "property", columnList = "GAME_NAME,COMPANY_NAME"),
        @Index(name = "company", columnList = "COMPANY_NAME")})
@ApiModel(value = "Game")
public class Game {

    @Id
    @GeneratedValue
    @Column(name = "ID")
    @JsonIgnore
    private Long gameId;

    @ApiModelProperty(notes = "Name of the game")
    @NotBlank(message = "Name of the game cannot be blank")
    @Column(name = "GAME_NAME")
    private String gameName;

    @Column(name = "COMPANY_NAME")
    @JsonIgnore
    private String companyName;

    @ApiModelProperty(notes = "Company endpoint for callback")
    @NotBlank(message = "Company endpoint cannot be blank")
    @Column(name = "RESPONSE_ENDPOINT")
    private String responseEndpoint;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinTable(name = "game_settings",
            joinColumns = { @JoinColumn(name = "game_id", referencedColumnName = "id") },
            inverseJoinColumns = { @JoinColumn(name = "settings_id", referencedColumnName = "id") })
    private GameSetting gameSetting;

    @Column(name = "GAME_STATUS")
    @JsonIgnore
    private String gameStatus;
}
