package com.pruqa.matchmakerpreparer.config;

import com.google.common.collect.Queues;
import com.pruqa.matchmakerpreparer.messanger.DefaultPlayerProducer;
import com.pruqa.matchmakerpreparer.messanger.FailureQueueMessageRecoverer;
import com.pruqa.matchmakerpreparer.messanger.PlayerProducer;
import org.aopalliance.aop.Advice;
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
    @Value("${app.rabbitmq.queue}")
    private String queueName;

    @Value("${app.rabbitmq.exchange}")
    private String exchange;

    @Value("${app.rabbitmq.routingkey}")
    private String routingKey;

    @Value("${app.result.rabbitmq.queue}")
    private String resultQueueName;

    @Value("${app.result.rabbitmq.exchange}")
    private String resultExchange;

    @Value("${app.result.rabbitmq.routingkey}")
    private String resultRoutingKey;

    private ConnectionFactory connectionFactory;

    @Autowired
    public RabbitConfig (final ConnectionFactory connectionFactory) {
        this.connectionFactory = connectionFactory;
    }

    // ==== beans ====
    /**
     * Defined the name of the queue and durability settings
     * @return queue name
     */
    @Bean
    Queue queue() {
        return new Queue(queueName, false);
    }

    /**
     * Exchange that will be used in the queue
     *
     * @return exchange name
     */
    @Bean
    DirectExchange exchange() {
        return new DirectExchange(exchange);
    }

    /**
     * Binding settings to build up dinamically a queue at first message sent
     * @param queue name to be used
     * @param exchange name to be used
     * @return binding
     */
    @Bean
    Binding binding(Queue queue, DirectExchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with(routingKey);
    }

    /**
     * Defined the name of the queue and durability settings
     * @return queue name
     */
    @Bean
    Queue failureQueue() {
        return new Queue(resultQueueName, false);
    }

    /**
     * Exchange that will be used in the queue
     *
     * @return successExchange name
     */
    @Bean
    DirectExchange failureExchange() {
        return new DirectExchange(resultExchange);
    }

    /**
     * Binding settings to build up dinamically a queue at first message sent
     * @param failureQueue name to be used
     * @param failureExchange name to be used
     * @return binding
     */
    @Bean
    Binding failureBinding(Queue failureQueue, DirectExchange failureExchange) {
        return BindingBuilder.bind(failureQueue).to(failureExchange).with(resultRoutingKey);
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
    public RabbitTemplate rabbitTemplate(final ConnectionFactory connectionFactory) {
        final var rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setExchange(exchange);
        rabbitTemplate.setRoutingKey(routingKey);
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
        rabbitTemplate.setExchange(resultExchange);
        rabbitTemplate.setRoutingKey(resultRoutingKey);
        rabbitTemplate.setMessageConverter(jsonMessageConverter());
        return rabbitTemplate;
    }

    @Bean
    public SimpleRabbitListenerContainerFactory listenerContainerFactory(PlayerProducer defaultPlayerProducer) {
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        factory.setConnectionFactory(connectionFactory);
        factory.setMessageConverter(jsonMessageConverter());
        factory.setConcurrentConsumers(3);
        factory.setMaxConcurrentConsumers(10);
        factory.setAdviceChain(retries(defaultPlayerProducer));
        return factory;
    }

    @Bean
    public RetryOperationsInterceptor retries(PlayerProducer defaultPlayerProducer) {
        return RetryInterceptorBuilder.stateless().maxAttempts(3)
                .backOffOptions(1000,3.0, 10000)
                .recoverer(new FailureQueueMessageRecoverer(defaultPlayerProducer))
                .build();
    }
}