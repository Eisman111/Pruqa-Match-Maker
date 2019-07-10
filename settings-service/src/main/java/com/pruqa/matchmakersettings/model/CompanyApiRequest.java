package com.pruqa.matchmakersettings.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
@ApiModel(value = "CompanyApiRequest")
public class CompanyApiRequest {

    @ApiModelProperty(notes = "Name of the game present on our database")
    @NotBlank(message = "Game name can not be blank")
    private String gameName;
}
