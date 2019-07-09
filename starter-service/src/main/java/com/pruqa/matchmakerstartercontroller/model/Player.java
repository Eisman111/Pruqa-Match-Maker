package com.pruqa.matchmakerstartercontroller.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.util.Map;

@Data
@ApiModel(value = "Player")
public class Player {

    @ApiModelProperty(notes = "Name of the game present on our database")
    private String gameName;

    @NotBlank(message = "Id is required")
    @ApiModelProperty(notes = "Id of the player")
    private String playerId;

    @NotEmpty(message = "Some characteristics is required")
    @ApiModelProperty(notes = "Characteristics of the player structured as a map characteristic:value. " +
            "A functional example is: level:10 logic:5 strategy:7")
    private Map<String,Double> characteristics;
}
