package net.lminar.jobservice.message.control;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;
import net.lminar.jobservice.job.entity.Message;

/**
 * Message sender.
 *
 * @author Lukas Minar
 */
@Slf4j
@Component
public class MessageSender {

	@Autowired
	private ExchangeManager exchangeManager;

	@Autowired
	private AmqpTemplate template;

	/**
	 * Sends message to specific queue name.
	 *
	 * @param message message
	 * @param routingKey routing key
	 */
	public void send(Message message, String routingKey) {
		exchangeManager.bindToExchange(routingKey);
		String exchange = exchangeManager.getExchange();

		log.debug("Sending message [{}] to exchange [{}] with routing key [{}].", message.getId(), exchange, routingKey);
		template.convertAndSend(exchange, routingKey, message);
	}
}
