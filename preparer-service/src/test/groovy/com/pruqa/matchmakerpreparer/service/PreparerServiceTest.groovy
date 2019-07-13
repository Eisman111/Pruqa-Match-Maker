package com.pruqa.matchmakerpreparer.service

import com.pruqa.matchmakerpreparer.exception.InvalidMatchMakingAlgorithmException
import com.pruqa.matchmakerpreparer.exception.UnavailablePreparationException
import com.pruqa.matchmakerpreparer.generated.SettingsControllerApi
import com.pruqa.matchmakerpreparer.generated.invoker.ApiException
import com.pruqa.matchmakerpreparer.generated.model.GameAttribute
import com.pruqa.matchmakerpreparer.generated.model.GameSetting
import com.pruqa.matchmakerpreparer.generated.model.SettingsRequest
import com.pruqa.matchmakerpreparer.model.MessagePlayer
import com.pruqa.matchmakerpreparer.model.Player

import com.pruqa.matchmakerpreparer.repository.PlayerRepository
import spock.lang.Specification

class PreparerServiceTest extends Specification {

    private PlayerRepository playerRepository
    private SettingsControllerApi settingsApi
    private IPreparerService service
    private MessagePlayer messagePlayer

    // ==== setup methods ====
    def setup() {
        playerRepository = Mock(PlayerRepository)
        settingsApi = Stub(SettingsControllerApi)
        service = new CounterPreparerService(settingsApi,playerRepository)
        messagePlayer = buildMessagePlayer()
    }

    // ==== test methods ====
//    def "Should save the enriched customer to the database"() {
//
//        given:
//        settingsApi.getGameRulesUsingPOST(_ as String, _ as String, _ as SettingsRequest) >> buildDefaultRules()
//
//        when:
//        Player player = service.prepareFlow(messagePlayer)
//
//        then:
//        1 * playerRepository.save(_ as Player)
//        player.getGameName() == "APEX"
//        player.getPlayerId() == 10
//        player.getPoints() == 11
//        player.getCharacteristics() == buildDefaultProperties()
//    }

    def "Should throw an exception in case the player can't have valid points"() {
        given:
        settingsApi.getGameRulesUsingPOST(_ as String, _ as String, _ as SettingsRequest) >> new ArrayList<GameAttribute>()

        when:
        Player player = service.prepareFlow(messagePlayer)

        then:
        thrown(InvalidMatchMakingAlgorithmException.class)
    }

    def "Should throw an exception in case the Settings Api are not reachable"() {


        given:
        settingsApi.getGameRulesUsingPOST(_ as String, _ as String, _ as SettingsRequest) >> {throw new ApiException()}

        when:
        Player player = service.prepareFlow(messagePlayer)

        then:
        thrown UnavailablePreparationException.class
    }

    def "Should throw an exception in case the DB is not reachable"() {

        setup:
        PlayerRepository stubbedRepository = Stub(PlayerRepository)
        IPreparerService updatedService = new CounterPreparerService(settingsApi,stubbedRepository)
        settingsApi.getGameRulesUsingPOST(_ as String, _ as String, _ as SettingsRequest) >> buildDefaultRules()
        stubbedRepository.save(_ as Player) >> {throw new RuntimeException()}

        when:
        Player player = updatedService.prepareFlow(messagePlayer)

        then:
        thrown UnavailablePreparationException.class
    }

    // ==== helper methods ====
    def buildMessagePlayer() {
        MessagePlayer player = new MessagePlayer()
        player.setGameName("APEX")
        player.setPlayerId("10")
        player.setCharacteristics(buildDefaultProperties())

        return player
    }

    def buildPlayer() {
        Player player = new Player()
        player.setGameName("APEX")
        player.setPlayerId("10")
        player.setCharacteristics(buildDefaultProperties())
        player.setPoints(11)
        return player
    }

    def buildDefaultProperties() {
        Map<String,Double> playerProperties = new HashMap<>()
        playerProperties.put("Intelligence",5d)
        playerProperties.put("Level",6.0d)
        return playerProperties
    }

    def buildDefaultRules() {
        GameSetting gameSetting = new GameSetting()
        List<GameAttribute> rules = new ArrayList<>()
        GameAttribute intelligence = new GameAttribute()
        intelligence.setAttributeName("Intelligence")
        intelligence.setAttributeMultiplier(1.0d)
        rules.add(intelligence)
        GameAttribute level = new GameAttribute()
        level.setAttributeName("Level")
        level.setAttributeMultiplier(1.0d)
        rules.add(level)
        gameSetting.setAttributes(rules)
        return gameSetting
    }
}
