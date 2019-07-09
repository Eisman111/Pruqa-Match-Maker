package com.pruqa.matchmakersettings.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

@Data
@ApiModel(value = "CompanyRequest")
public class CompanyRequest {

    @ApiModelProperty(notes = "Name of the company is necessary to identify your settings")
    @NotBlank(message = "Company name can not be blank")
    private String companyName;

    @ApiModelProperty(notes = "Name of the game present on our database")
    @NotBlank(message = "Game name can not be blank")
    private String gameName;
}
