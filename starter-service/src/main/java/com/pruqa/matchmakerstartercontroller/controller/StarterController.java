package com.pruqa.matchmakerstartercontroller.controller;

import com.pruqa.matchmakerstartercontroller.model.Player;
import com.pruqa.matchmakerstartercontroller.service.StarterControllerInterfaceService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.security.Principal;

@Slf4j
@RestController
@Api(value = "starter-controller")
@RequestMapping(value = "/api/v1")
public class StarterController {

    // ==== fields ====
    private StarterControllerInterfaceService service;

    // ==== constructor ====
    /**
     * Constructor of the controller with injected service
     * @param service is injected based on the bean in the CommonConfig
     */
    @Autowired
    private StarterController(StarterControllerInterfaceService service) {
        this.service = service;
    }

    // ==== controllers ====
    /**
     * Endpoint that add the player to the match maker service following the flow defined in the bean
     *
     * @param player request objected
     * @return ResponseEntity<String>
     */
    @ApiOperation(value = "Add a player to the queue",
            notes = "This operation can be used to queue a player to the match making service")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "The service received correctly the request"),
            @ApiResponse(code = 401, message = "Unauthorized access, missing credentials"),
            @ApiResponse(code = 403, message = "Authorization failed, wrong credentials")
    })
    @RequestMapping(value = "/public/players/add", method = RequestMethod.POST)
    public ResponseEntity<String> addPlayerToQueue (Principal principal, @Valid @RequestBody Player player) {
        return ResponseEntity
                .ok()
                .body(service.addPlayerToMatchMakerFlow(principal, player));
    }
}
