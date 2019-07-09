package com.pruqa.matchmakersettings.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@ApiModel(value = "SettingsRequest")
public class SettingsRequest {

    @ApiModelProperty(notes = "Name of the game present on our database")
    private String gameName;
}
