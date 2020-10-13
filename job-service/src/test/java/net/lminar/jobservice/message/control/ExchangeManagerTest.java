package net.lminar.jobservice.message.control;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.Queue;
import org.springframework.test.util.ReflectionTestUtils;

/**
 * Unit test for {@link ExchangeManager}.
 *
 * @author Lukas Minar
 */
@ExtendWith(MockitoExtension.class)
class ExchangeManagerTest {

	@Mock
	private AmqpAdmin admin;

	@InjectMocks
	private ExchangeManager tested;

	@BeforeEach
	void setup() {
		ReflectionTestUtils.setField(tested, "exchange", "job-exchange");
		ReflectionTestUtils.setField(tested, "queueSuffix", "-queue");
		ReflectionTestUtils.setField(tested, "defaultQueueName", "job-queue");
		ReflectionTestUtils.setField(tested, "defaultRoutingKey", "");
	}

	@Test
	void bindToExchange_ok() {
		tested.bindToExchange("routingKey");

		ArgumentCaptor<Queue> queueCaptor = ArgumentCaptor.forClass(Queue.class);
		verify(admin).declareQueue(queueCaptor.capture());

		assertEquals("routingKey-queue", queueCaptor.getValue().getName());

		ArgumentCaptor<Binding> bindingCaptor = ArgumentCaptor.forClass(Binding.class);
		verify(admin).declareBinding(bindingCaptor.capture());

		assertEquals("job-exchange", bindingCaptor.getValue().getExchange());
		assertEquals("routingKey", bindingCaptor.getValue().getRoutingKey());
		assertEquals("routingKey-queue", bindingCaptor.getValue().getDestination());
	}

	@Test
	void bindToExchange_secondBinding() {
		tested.bindToExchange("routingKey");
		tested.bindToExchange("routingKey");

		ArgumentCaptor<Queue> queueCaptor = ArgumentCaptor.forClass(Queue.class);
		verify(admin).declareQueue(queueCaptor.capture());

		assertEquals("routingKey-queue", queueCaptor.getValue().getName());

		ArgumentCaptor<Binding> bindingCaptor = ArgumentCaptor.forClass(Binding.class);
		verify(admin).declareBinding(bindingCaptor.capture());

		assertEquals("job-exchange", bindingCaptor.getValue().getExchange());
		assertEquals("routingKey", bindingCaptor.getValue().getRoutingKey());
		assertEquals("routingKey-queue", bindingCaptor.getValue().getDestination());
	}

	@Test
	void bindToExchange_defaultRoutingKey() {
		tested.bindToExchange("");

		ArgumentCaptor<Queue> queueCaptor = ArgumentCaptor.forClass(Queue.class);
		verify(admin).declareQueue(queueCaptor.capture());

		assertEquals("job-queue", queueCaptor.getValue().getName());

		ArgumentCaptor<Binding> bindingCaptor = ArgumentCaptor.forClass(Binding.class);
		verify(admin).declareBinding(bindingCaptor.capture());

		assertEquals("job-exchange", bindingCaptor.getValue().getExchange());
		assertEquals("", bindingCaptor.getValue().getRoutingKey());
		assertEquals("job-queue", bindingCaptor.getValue().getDestination());
	}

	@Test
	void getExchange() {
		String result = tested.getExchange();

		assertEquals("job-exchange", result);
	}
}