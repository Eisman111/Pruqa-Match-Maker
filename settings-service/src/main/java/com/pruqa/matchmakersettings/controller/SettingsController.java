package com.pruqa.matchmakersettings.controller;

import com.pruqa.matchmakersettings.model.*;
import com.pruqa.matchmakersettings.service.SettingsServiceInterface;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;

@Slf4j
@RestController
@Api(value = "settings-controller")
@RequestMapping(value = "/api/v1")
public class SettingsController {

    // ==== fields ====
    private SettingsServiceInterface settingsService;

    // ==== constructor ====
    @Autowired
    public SettingsController (final SettingsServiceInterface settingsService) {
        this.settingsService = settingsService;
    }

    // ==== controllers ====
    /**
     * Endpoint for recovering the game rules
     *
     * @param settingsRequest request objected
     * @return generic response of type gameResponse
     */
    @ApiOperation(value = "Recover the game Rules",
            notes = "This operation can be used to recover the Game Rules")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "The service received correctly the request"),
            @ApiResponse(code = 404, message = "Game not found by company ID and game ID"),
            @ApiResponse(code = 406, message = "Game settings and rules are not valid")
    })
    @RequestMapping(value = "/internal/games/get", method = RequestMethod.POST)
    public ResponseEntity<GameSetting> getGameRules (@Valid @RequestBody SettingsRequest settingsRequest) {
        return ResponseEntity.ok(settingsService.fetchGameSettings(settingsRequest));
    }

    /**
     * Endpoint for verifying the company details
     *
     * @param companyRequest request objected
     * @return generic response of type gameResponse
     */
    @ApiOperation(value = "Verify the company",
            notes = "This operation can be used to verify the company and the game definition")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "The service received correctly the request"),
            @ApiResponse(code = 404, message = "Game not found by company ID and game ID"),
            @ApiResponse(code = 406, message = "Game settings and rules are not valid")
    })
    @RequestMapping(value = "/internal/companies/verify", method = RequestMethod.POST)
    public ResponseEntity<String> verifyCompany (@RequestHeader("sessionId") String sessionId,
                                                 @RequestHeader("requestId") String requestId,
                                                 @Valid @RequestBody CompanyRequest companyRequest) {
        log.info("SessionId {}, requestId {}",sessionId,requestId);
        settingsService.verifyValidityCompanyAndGame(companyRequest);
        return ResponseEntity.ok("Everything is set correctly");
    }

    /**
     * Endpoint for recovering the company api settings
     *
     * @param companyApiRequest request objected
     * @return generic response of type gameResponse
     */
    @ApiOperation(value = "Recover the company api settings",
            notes = "This operation can be used to recover the company api settings")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "The service received correctly the request"),
            @ApiResponse(code = 404, message = "Game not found"),
            @ApiResponse(code = 406, message = "Settings are not valid")
    })
    @RequestMapping(value = "/internal/company/get", method = RequestMethod.POST)
    public ResponseEntity<CompanyApiResponse> getCompanyApiSettings (@Valid @RequestBody CompanyApiRequest companyApiRequest) {
        return ResponseEntity.ok(settingsService.fetchCompanyApi(companyApiRequest));
    }

    /**
     * Endpoint for verifying the company details
     *
     * @param game request objected
     * @return addGameResponse with the game id and company id
     */
    @ApiOperation(value = "Add a game to the list",
            notes = "This operation can be used to add a game to the system")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "The service received correctly the request")
    })
    @RequestMapping(value = "/public/games/add", method = RequestMethod.POST)
    public ResponseEntity<GameResponse> addGame (Principal principal, @Valid @RequestBody Game game) {
        return ResponseEntity.ok(settingsService.saveGame(principal,game));
    }
}
