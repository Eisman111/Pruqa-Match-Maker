//package com.pruqa.matchmakerpreparer.service
//
//import com.pruqa.matchmakerpreparer.exception.InvalidMatchMakingAlgorithmException
//import com.pruqa.matchmakerpreparer.exception.UnavailablePreparationException
//import com.pruqa.matchmakerpreparer.generated.SettingsControllerApi
//import com.pruqa.matchmakerpreparer.generated.invoker.ApiException
//import com.pruqa.matchmakerpreparer.generated.model.GameRulesRequest
//import com.pruqa.matchmakerpreparer.generated.model.Rule
//import com.pruqa.matchmakerpreparer.model.MessagePlayer
//import com.pruqa.matchmakerpreparer.model.Player
//
//import com.pruqa.matchmakerpreparer.repository.PlayerRepository
//import spock.lang.Ignore
//import spock.lang.Specification
//
//@Ignore
//class PreparerServiceTest extends Specification {
//
//    private PlayerRepository playerRepository
//    private SettingsControllerApi settingsApi
//    private IPreparerService service
//    private MessagePlayer messagePlayer
//
//    // ==== setup methods ====
//    def setup() {
//        playerRepository = Mock(PlayerRepository)
//        settingsApi = Stub(SettingsControllerApi)
//        service = new CounterPreparerService(settingsApi,playerRepository)
//        messagePlayer = buildMessagePlayer()
//    }
//
//    // ==== test methods ====
//    def "Should save the enriched customer to the database"() {
//
//        given:
//        settingsApi.getGameRulesUsingPOST(_ as String, _ as String, _ as GameRulesRequest) >> buildDefaultRules()
//
//        when:
//        Player player = service.prepareFlow(messagePlayer)
//
//        then:
//        1 * playerRepository.save(_ as Player)
//        player.getGameId() == 10
//        player.getPlayerId() == 10
//        player.getPoints() == 11
//        player.getCharacteristics() == buildDefaultProperties()
//    }
//
//    def "Should throw an exception in case the player can't have valid points"() {
//        given:
//        settingsApi.getGameRulesUsingPOST(_ as String, _ as String, _ as GameRulesRequest) >> new ArrayList<Rule>()
//
//        when:
//        Player player = service.prepareFlow(messagePlayer)
//
//        then:
//        thrown(InvalidMatchMakingAlgorithmException.class)
//    }
//
//    def "Should throw an exception in case the Settings Api are not reachable"() {
//
//
//        given:
//        settingsApi.getGameRulesUsingPOST(_ as String, _ as String, _ as GameRulesRequest) >> {throw new ApiException()}
//
//        when:
//        Player player = service.prepareFlow(messagePlayer)
//
//        then:
//        thrown UnavailablePreparationException.class
//    }
//
//    def "Should throw an exception in case the DB is not reachable"() {
//
//        setup:
//        PlayerRepository stubbedRepository = Stub(PlayerRepository)
//        IPreparerService updatedService = new CounterPreparerService(settingsApi,stubbedRepository)
//        settingsApi.getGameRulesUsingPOST(_ as String, _ as String, _ as GameRulesRequest) >> buildDefaultRules()
//        stubbedRepository.save(_ as Player) >> {throw new RuntimeException()}
//
//        when:
//        Player player = updatedService.prepareFlow(messagePlayer)
//
//        then:
//        thrown UnavailablePreparationException.class
//    }
//
//    // ==== helper methods ====
//    def buildMessagePlayer() {
//        MessagePlayer player = new MessagePlayer()
//        player.setGameId(10)
//        player.setPlayerId(10)
//        player.setCharacteristics(buildDefaultProperties())
//
//        return player
//    }
//
//    def buildPlayer() {
//        Player player = new Player()
//        player.setGameId(10)
//        player.setPlayerId(10)
//        player.setCharacteristics(buildDefaultProperties())
//        player.setPoints(11)
//        return player
//    }
//
//    def buildDefaultProperties() {
//        Map<String,String> playerProperties = new HashMap<>()
//        playerProperties.put("Intelligence","5")
//        playerProperties.put("Level","6")
//        return playerProperties
//    }
//
//    def buildDefaultRules() {
//        List<Rule> rules = new ArrayList<>()
//        Rule intelligence = new Rule()
//        intelligence.setName("Intelligence")
//        intelligence.setValue("1")
//        rules.add(intelligence)
//        Rule level = new Rule()
//        level.setName("Level")
//        level.setValue("1")
//        rules.add(level)
//        return rules
//    }
//}
