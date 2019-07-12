package com.pruqa.acmedemocompany.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;
import org.springframework.lang.Nullable;

import javax.validation.constraints.NotBlank;

@Data
@Builder
@ApiModel(value = "MatchMakerResult")
public class MatchMakerResult {

    @ApiModelProperty(notes = "The response of the match making operation for the playerOne")
    private ResponseCode responseCode;

    @ApiModelProperty(notes = "The response message that support the responseCode")
    private String responseMessage;

    @ApiModelProperty(notes = "The game name referring to the the inputPlayer and matchedPlayer")
    @NotBlank(message = "The game name cannot be blank")
    private String gameName;

    @ApiModelProperty(notes = "The player tried to match with another player on the queue")
    @NotBlank(message = "The first player cannot be blank")
    private String inputPlayer;

    @ApiModelProperty(notes = "The player matched to the input player. Can be null in case of errors")
    @Nullable
    private String matchedPlayer;
}
