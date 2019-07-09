package com.pruqa.matchmakerstartercontroller.config;

import com.pruqa.matchmakerstartercontroller.generated.SettingsControllerApi;
import com.pruqa.matchmakerstartercontroller.messanger.DefaultMessageProducer;
import com.pruqa.matchmakerstartercontroller.messanger.MessageProducer;
import com.pruqa.matchmakerstartercontroller.service.DefaultStarterControllerService;
import com.pruqa.matchmakerstartercontroller.service.StarterControllerInterfaceService;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CommonConfig {

    // ==== fields ====
    private RabbitTemplate rabbitTemplate;

    // ==== constructor ====
    /**
     * Constructor injection of rabbit template bean that will be used by the producer
     * @param rabbitTemplate template necessary to send messages to the queue
     */
    @Autowired
    public CommonConfig (final RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    // ==== beans ====
    /**
     * Define the service that will be used in the logic
     *
     * @return starterController service
     */
    @Bean
    public StarterControllerInterfaceService service () {
        return new DefaultStarterControllerService(messageProducer(),settingsApi());
    }

    /**
     * Prepare the producer necessary to send out messages to the rabbit queue
     *
     * @return messageProducer
     */
    @Bean
    public MessageProducer messageProducer() {
        return new DefaultMessageProducer(rabbitTemplate);
    }

    /**
     * Access to the Settings Service API
     * @return settingsControllerApi
     */
    @Bean
    public SettingsControllerApi settingsApi() {
        return new SettingsControllerApi();
    }
}
