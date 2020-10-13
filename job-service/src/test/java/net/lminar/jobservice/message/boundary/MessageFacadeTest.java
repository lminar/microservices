package net.lminar.jobservice.message.boundary;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import net.lminar.jobservice.job.entity.Message;
import net.lminar.jobservice.message.control.MessageSender;

/**
 * Unit test for {@link MessageFacade}.
 *
 * @author Lukas Minar
 */
@ExtendWith(MockitoExtension.class)
class MessageFacadeTest {

	@Mock
	private MessageSender messageSender;

	@InjectMocks
	private MessageFacade tested;

	@Test
	void sendMessage_ok() {
		Message message = mock(Message.class);

		boolean result = tested.sendMessage(message, "routingKey");

		verify(messageSender).send(message, "routingKey");

		assertTrue(result);
	}

	@Test
	void sendMessage_failed() {
		Message message = mock(Message.class);

		doThrow(RuntimeException.class).when(messageSender).send(message, "routingKey");

		boolean result = tested.sendMessage(message, "routingKey");

		assertFalse(result);
	}
}