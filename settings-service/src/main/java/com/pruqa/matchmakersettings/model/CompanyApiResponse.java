package com.pruqa.matchmakersettings.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
@Builder
@ApiModel(value = "CompanyApiResponse")
public class CompanyApiResponse {

    @ApiModelProperty(notes = "Name of the company is necessary to identify your settings")
    @NotBlank(message = "Company name can not be blank")
    private String companyName;

    @ApiModelProperty(notes = "Company endpoint for callback for response")
    @NotBlank(message = "Company endpoint cannot be blank")
    private String responseEndpoint;
}
