package net.lminar.jobservice.message.control;

import java.util.HashSet;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

/**
 * Exchange manager.
 *
 * @author Lukas Minar
 */
@Slf4j
@Component
public class ExchangeManager {

	@Autowired
	private AmqpAdmin admin;

	@Value("${job.rabbitmq.exchange:job-exchange}")
	private String exchange;

	@Value("${job.rabbitmq.queue-suffix:-queue}")
	private String queueSuffix;

	@Value("${job.rabbitmq.default.queue-name:job-queue}")
	private String defaultQueueName;

	@Value("${job.rabbitmq.default.routing-key:}")
	private String defaultRoutingKey;

	/**
	 * Contains processed bindings.
	 */
	private Set<String> bindings = new HashSet<>();

	/**
	 * Bind routing key to exchange.
	 *
	 * @param routingKey routing key
	 */
	void bindToExchange(String routingKey) {
		if (bindings.contains(routingKey)) {
			return;
		}

		String queueName = getQueueName(routingKey);
		bindToExchange(queueName, routingKey);
	}

	/**
	 * Gets queue name for routing key.
	 *
	 * @param routingKey routing key
	 * @return queue name
	 */
	private String getQueueName(String routingKey) {
		if (StringUtils.equals(routingKey, defaultRoutingKey)) {
			return defaultQueueName;
		}

		return routingKey + queueSuffix;
	}

	/**
	 * Binds queue to exchange with routing key.
	 *
	 * @param queueName queue name
	 * @param routingKey routing key
	 */
	private void bindToExchange(String queueName, String routingKey) {
		log.debug("Binding destination queue [{}] to exchange [{}] with routing key [{}].", queueName, exchange, routingKey);

		Queue queue = new Queue(queueName);
		admin.declareQueue(queue);

		Binding binding = new Binding(queueName, Binding.DestinationType.QUEUE, exchange, routingKey, null);
		admin.declareBinding(binding);

		bindings.add(routingKey);
	}

	/**
	 * Gets exchange.
	 *
	 * @return exchange
	 */
	String getExchange() {
		return exchange;
	}
}
