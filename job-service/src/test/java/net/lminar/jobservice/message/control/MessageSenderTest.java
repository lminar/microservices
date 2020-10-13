package net.lminar.jobservice.message.control;

import static org.mockito.Mockito.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.amqp.core.AmqpTemplate;

import net.lminar.jobservice.job.entity.Message;

/**
 * Unit test for {@link MessageSender}.
 *
 * @author Lukas Minar
 */
@ExtendWith(MockitoExtension.class)
class MessageSenderTest {

	@Mock
	private ExchangeManager exchangeManager;

	@Mock
	private AmqpTemplate template;

	@InjectMocks
	private MessageSender tested;

	@Test
	void send() {
		Message message = mock(Message.class);
		when(exchangeManager.getExchange()).thenReturn("exchange");

		tested.send(message, "routingKey");

		verify(exchangeManager).bindToExchange("routingKey");
		verify(exchangeManager).getExchange();
		verify(template).convertAndSend("exchange",  "routingKey", message);
	}
}