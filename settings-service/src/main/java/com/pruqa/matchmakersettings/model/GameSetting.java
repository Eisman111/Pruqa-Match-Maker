package com.pruqa.matchmakersettings.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.*;
import java.util.Set;

@Data
@Entity
@ApiModel(value = "GameSetting")
public class GameSetting {

    // ==== fields ====
    @Id
    @GeneratedValue
    @Column(name = "ID")
    @JsonIgnore
    private Long id;

    @ApiModelProperty(notes = "Mode of the game")
    @Column(name = "GAME_MODE")
    private GameMode matchMode;

    @ElementCollection
    @CollectionTable(name="GAME_ATTRIBUTES")
    private Set<GameAttribute> attributes;

    @ApiModelProperty(notes = "Absolute range of verification for the entire attributes")
    @Column(name = "ABSOLUTE_RANGE")
    private Double absoluteRange;

    @ApiModelProperty(notes = "Percentage range of verification for the entire attributes")
    @Column(name = "PERCENTAGE_RANGE")
    private Double percentageRange;

    @ApiModelProperty(notes = "Wait time in milliseconds before increasing match search")
    @Column(name = "INCREMENTAL_WAIT_TIME")
    private int incrementalWaitTime;

    @ApiModelProperty(notes = "Number of rounds of incrementation for match making")
    @Column(name = "INCREMENTAL_ROUNDS")
    private int incrementalRounds;
}
