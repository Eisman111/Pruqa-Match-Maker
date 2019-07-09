package com.pruqa.matchmakerstartercontroller.service;

import com.pruqa.matchmakerstartercontroller.exception.InvalidGameException;
import com.pruqa.matchmakerstartercontroller.generated.SettingsControllerApi;
import com.pruqa.matchmakerstartercontroller.generated.invoker.ApiException;
import com.pruqa.matchmakerstartercontroller.generated.model.CompanyRequest;
import com.pruqa.matchmakerstartercontroller.messanger.MessageProducer;
import com.pruqa.matchmakerstartercontroller.model.Player;
import lombok.extern.slf4j.Slf4j;

import java.security.Principal;

@Slf4j
public class DefaultStarterControllerService extends StarterControllerService {

    // ==== fields ====
    private MessageProducer messageProducer;
    private SettingsControllerApi settingsApi;

    // ==== constructors ====
    /**
     * This constructor is initialized by spring as bean with the message producer injected
     * @param messageProducer messange queuer
     */
    public DefaultStarterControllerService (final MessageProducer messageProducer, final SettingsControllerApi settingsApi)  {
        this.messageProducer = messageProducer;
        this.settingsApi = settingsApi;
    }

    /**
     * Verify the game settings and rules
     * @param principal object defined in the request
     * @param player object defined in the request
     */
    @Override
    void verifyGameAccessibilityAndRules(final Principal principal, final Player player) {
        CompanyRequest companyRequest = new CompanyRequest();
        companyRequest.setCompanyName(principal.getName());
        companyRequest.setGameName(player.getGameName());
        try {
            settingsApi.verifyCompanyUsingPOST("requestid","sessionId",companyRequest);
            log.info("Successfully verified game accessibility and rules");
        } catch (ApiException e) {
            log.error("Failed to verify game accessibility and rules, error {}", e.getResponseBody());
            throw new InvalidGameException();
        }
    }

    /**
     * Add the player to the defined queue
     * @param player object defined in the request
     * @return addPlayerResponse
     */
    @Override
    String addPlayerToQueue(Player player) {
        log.info(player.toString());
        messageProducer.produceMsg(player);
        return "ok";
    }
}
