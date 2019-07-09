package com.pruqa.matchmakerpreparer.messanger

import com.pruqa.matchmakerpreparer.exception.InvalidMatchMakingAlgorithmException
import com.pruqa.matchmakerpreparer.exception.UnavailablePreparationException
import com.pruqa.matchmakerpreparer.model.MessagePlayer
import com.pruqa.matchmakerpreparer.model.Player
import com.pruqa.matchmakerpreparer.service.IPreparerService
import spock.lang.Ignore
import spock.lang.Specification

@Ignore
class PlayerConsumerTest extends Specification {

    private IPreparerService service
    private PlayerConsumer consumer
    private PlayerProducer producer

    def setup() {
        service = Stub(IPreparerService)
        producer = Mock(PlayerProducer)
        consumer = new DefaultPlayerConsumer(service, producer)
    }

    def "Should return the player once has been correctly managed by the service"() {
        given:
        service.prepareFlow(_ as MessagePlayer) >> buildPlayer()

        when:
        Player player = consumer.readMessage(buildMessagePlayer())

        then:
        player.getPlayerId() == 10
        player.getGameId() == 10
        player.getPoints() == 11
    }

    def "Should add in error queue the player if the service can't provide a match for it"() {
        given:
        service.prepareFlow(_ as MessagePlayer) >> {throw new InvalidMatchMakingAlgorithmException()}

        when:
        Player player = consumer.readMessage(buildMessagePlayer())

        then:
        1 * producer.addToErrorQueue()
    }

    def "Should add in a retry queue the player if the service was unavailable but still usable"() {
        given:
        service.prepareFlow(_ as MessagePlayer) >> {throw new UnavailablePreparationException()}

        when:
        Player player = consumer.readMessage(buildMessagePlayer())

        then:
        1 * producer.addToRetryQueue()
    }

    // ==== helper methods ====
    def buildMessagePlayer() {
        MessagePlayer player = new MessagePlayer()
        player.setGameId(10)
        player.setPlayerId(10)
        player.setCharacteristics(buildDefaultProperties())

        return player
    }

    def buildPlayer() {
        Player player = new Player()
        player.setGameId(10)
        player.setPlayerId(10)
        player.setCharacteristics(buildDefaultProperties())
        player.setPoints(11)
        return player
    }

    def buildDefaultProperties() {
        Map<String,String> playerProperties = new HashMap<>()
        playerProperties.put("Intelligence","5")
        playerProperties.put("Level","6")
        return playerProperties
    }
}
