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
    @Value("${app.result.rabbitmq.queue}")
    private String queueName;

    @Value("${app.result.rabbitmq.exchange}")
    private String exchange;

    @Value("${app.result.rabbitmq.routingkey}")
    private String routingKey;

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
    public DefaultCombinerProducer messageProducer(RabbitTemplate rabbitTemplate) {
        return new DefaultCombinerProducer(rabbitTemplate);
    }
}