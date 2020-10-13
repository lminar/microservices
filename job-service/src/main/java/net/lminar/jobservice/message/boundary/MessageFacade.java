package net.lminar.jobservice.message.boundary;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;
import net.lminar.jobservice.message.control.MessageSender;
import net.lminar.jobservice.job.entity.Message;

/**
 * Message facade.
 *
 * @author Lukas Minar
 */
@Slf4j
@Component
public class MessageFacade {

	@Autowired
	private MessageSender messageSender;

	/**
	 * Send message to queue.
	 *
	 * @param message message
	 * @param routingKey routing key
	 * @return true if message send
	 */
	public boolean sendMessage(Message message, String routingKey) {
		try {
			log.debug("Send message [{}] to exchange with routing key [{}].", message.getId(), routingKey);
			messageSender.send(message, routingKey);
			return true;
		} catch (Exception e) {
			log.error("Unable to send message [{}] to exchange with routing key [{}].", message.getId(), routingKey);
			return false;
		}
	}
}
