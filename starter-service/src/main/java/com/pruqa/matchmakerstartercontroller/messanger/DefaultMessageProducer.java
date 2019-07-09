package com.pruqa.matchmakerstartercontroller.messanger;

import org.springframework.amqp.rabbit.core.RabbitTemplate;

public class DefaultMessageProducer implements MessageProducer {

    // ==== fields ====
    private RabbitTemplate rabbitTemplate;

    // ==== constructors ====
    /**
     * This constructors is started as bean by spring so that rabbit template is automatically injected
     * @param rabbitTemplate template necessary to send messages to the queue
     */
    public DefaultMessageProducer (RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    @Override
    public void produceMsg(Object msg){
        rabbitTemplate.convertAndSend(msg);
    }
}
