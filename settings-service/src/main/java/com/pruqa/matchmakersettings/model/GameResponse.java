package com.pruqa.matchmakersettings.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@ApiModel("GameResponse")
public class GameResponse {

    // === fields ====
    @ApiModelProperty(notes = "Input Company name")
    private String companyName;

    @ApiModelProperty(notes = "Input Game name")
    private String gameName;
}
