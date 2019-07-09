package com.pruqa.matchmakerstartercontroller.controller

import spock.lang.Specification

class StarterControllerTest extends Specification {

    def "AddPlayerToQueue"() {
        given:
        int left = 2
        int right = 2

        when:
        int result = left + right

        then:
        result == 4
    }
}
