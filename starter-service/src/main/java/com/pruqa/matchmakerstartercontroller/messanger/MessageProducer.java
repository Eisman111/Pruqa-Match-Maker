package com.pruqa.matchmakerstartercontroller.messanger;

public interface MessageProducer {

    /**
     * Send message to the queue for match making
     *
     * @param msg generic object that will be converted in a json
     */
    void produceMsg(Object msg);
}
