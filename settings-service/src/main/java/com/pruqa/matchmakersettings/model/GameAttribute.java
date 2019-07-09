package com.pruqa.matchmakersettings.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@ApiModel(value = "GameAttribute")
public class GameAttribute {

    // ==== fields ====
    @Id
    @GeneratedValue
    @Column(name = "ID")
    @JsonIgnore
    private Long id;

    @ApiModelProperty(notes = "Name of the attribute")
    @Column(name = "ATTRIBUTE_NAME")
    private String attributeName;

    @ApiModelProperty(notes = "Attribute multiplier")
    @Column(name = "ATTRIBUTE_MULTIPLIER")
    private Double attributeMultiplier;

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
