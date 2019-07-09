package com.pruqa.matchmakercombiner.config;

import com.pruqa.matchmakercombiner.messanger.CombinerProducer;
import com.pruqa.matchmakercombiner.messanger.DefaultCombinerProducer;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.config.RetryInterceptorBuilder;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.retry.RejectAndDontRequeueRecoverer;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.retry.interceptor.RetryOperationsInterceptor;

@Configuration
public class RabbitConfig {

    // ==== fields ====
    @Value("${app.success.rabbitmq.queue}")
    private String successQueueName;

    @Value("${app.success.rabbitmq.exchange}")
    private String successExchange;

    @Value("${app.success.rabbitmq.routingkey}")
    private String successRoutingKey;

    @Value("${app.failure.rabbitmq.queue}")
    private String failureQueueName;

    @Value("${app.failure.rabbitmq.exchange}")
    private String failureExchange;

    @Value("${app.failure.rabbitmq.routingkey}")
    private String failureRoutingKey;

    private ConnectionFactory connectionFactory;

    @Autowired
    public RabbitConfig(final ConnectionFactory connectionFactory) {
        this.connectionFactory = connectionFactory;
    }

    // ==== beans ====
    /**
     * Defined the name of the queue and durability settings
     * @return queue name
     */
    @Bean
    Queue successQueue() {
        return new Queue(successQueueName, false);
    }

    /**
     * Exchange that will be used in the queue
     *
     * @return successExchange name
     */
    @Bean
    DirectExchange successExchange() {
        return new DirectExchange(successExchange);
    }

    /**
     * Binding settings to build up dinamically a queue at first message sent
     * @param successQueue name to be used
     * @param successExchange name to be used
     * @return binding
     */
    @Bean
    Binding successBinding(Queue successQueue, DirectExchange successExchange) {
        return BindingBuilder.bind(successQueue).to(successExchange).with(successRoutingKey);
    }

    /**
     * Defined the name of the queue and durability settings
     * @return queue name
     */
    @Bean
    Queue failureQueue() {
        return new Queue(failureQueueName, false);
    }

    /**
     * Exchange that will be used in the queue
     *
     * @return successExchange name
     */
    @Bean
    DirectExchange failureExchange() {
        return new DirectExchange(failureExchange);
    }

    /**
     * Binding settings to build up dinamically a queue at first message sent
     * @param failureQueue name to be used
     * @param failureExchange name to be used
     * @return binding
     */
    @Bean
    Binding failureBinding(Queue failureQueue, DirectExchange failureExchange) {
        return BindingBuilder.bind(failureQueue).to(failureExchange).with(failureRoutingKey);
    }

    /**
     * JSON converter for messages that are sent to the rabbit queue
     *
     * @return json message converter
     */
    @Bean
    public MessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    /**
     * Build the template that is required to send out messages to the queue defined in the properties
     *
     * @param connectionFactory default connection factory for rabbitmq
     * @return rabbitTemplate
     */
    @Bean
    public RabbitTemplate successRabbitTemplate(final ConnectionFactory connectionFactory) {
        final var rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setExchange(successExchange);
        rabbitTemplate.setRoutingKey(successRoutingKey);
        rabbitTemplate.setMessageConverter(jsonMessageConverter());
        return rabbitTemplate;
    }

    /**
     * Build the template that is required to send out messages to the queue defined in the properties
     *
     * @param connectionFactory default connection factory for rabbitmq
     * @return rabbitTemplate
     */
    @Bean
    public RabbitTemplate failureRabbitTemplate(final ConnectionFactory connectionFactory) {
        final var rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setExchange(failureExchange);
        rabbitTemplate.setRoutingKey(failureRoutingKey);
        rabbitTemplate.setMessageConverter(jsonMessageConverter());
        return rabbitTemplate;
    }

    @Bean
    public SimpleRabbitListenerContainerFactory listenerContainerFactory() {
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        factory.setConnectionFactory(connectionFactory);
        factory.setMessageConverter(jsonMessageConverter());
        factory.setConcurrentConsumers(3);
        factory.setMaxConcurrentConsumers(10);
        factory.setAdviceChain(retries());
        return factory;
    }

    @Bean
    public RetryOperationsInterceptor retries() {
        return RetryInterceptorBuilder.stateless().maxAttempts(3)
                .backOffOptions(1000,
                        3.0, 10000)
                .recoverer(new RejectAndDontRequeueRecoverer()).build();  //TODO write a error requeue policy
    }

    /**
     * Prepare the producer necessary to send out messages to the rabbit queue
     *
     * @return messageProducer
     */
    @Bean
    public DefaultCombinerProducer messageProducer(RabbitTemplate successRabbitTemplate, RabbitTemplate failureRabbitTemplate) {
        return new DefaultCombinerProducer(successRabbitTemplate,failureRabbitTemplate);
    }
}