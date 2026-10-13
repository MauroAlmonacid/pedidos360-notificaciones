package cl.duoc.pedidos360.notificaciones.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

	@Value("${app.rabbitmq.queue}")
	private String queueName;

	@Value("${app.rabbitmq.exchange}")
	private String exchangeName;

	@Value("${app.rabbitmq.routingkey}")
	private String routingKey;

	@Bean
	public Queue notificacionesQueue() {
		return new Queue(queueName, true);
	}

	@Bean
	public TopicExchange pedidosExchange() {
		return new TopicExchange(exchangeName, true, false);
	}

	@Bean
	public Binding notificacionesBinding(Queue notificacionesQueue, TopicExchange pedidosExchange) {
		return BindingBuilder.bind(notificacionesQueue).to(pedidosExchange).with(routingKey);
	}

	@Bean
	public MessageConverter jsonMessageConverter() {
		return new Jackson2JsonMessageConverter();
	}

	@Bean
	public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
		RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
		rabbitTemplate.setMessageConverter(jsonMessageConverter());
		return rabbitTemplate;
	}

	@Bean
	public SimpleRabbitListenerContainerFactory rabbitListenerContainerFactory(ConnectionFactory connectionFactory) {
		SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
		factory.setConnectionFactory(connectionFactory);
		factory.setMessageConverter(jsonMessageConverter());
		return factory;
	}
}
