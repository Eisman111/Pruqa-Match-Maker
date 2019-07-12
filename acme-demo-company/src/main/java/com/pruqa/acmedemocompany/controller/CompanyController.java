package com.pruqa.acmedemocompany.controller;

import com.pruqa.acmedemocompany.model.MatchMakerResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Slf4j
@RestController
@Api(value = "demo-company-controller")
@RequestMapping(value = "/matchmaking/v1")
public class CompanyController {

    @ApiOperation(value = "Recover the game Rules",
            notes = "This operation can be used to recover the Game Rules")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "The service received correctly the request"),
            @ApiResponse(code = 404, message = "Game not found by company ID and game ID"),
            @ApiResponse(code = 406, message = "Game settings and rules are not valid")
    })
    @RequestMapping(value = "/result", method = RequestMethod.POST)
    public ResponseEntity<String> getMatchMakingResult (@Valid @RequestBody MatchMakerResult matchMakerResult) {
        log.info("Result of match making {}", matchMakerResult.toString());
        return ResponseEntity.ok("OK");
    }
}
