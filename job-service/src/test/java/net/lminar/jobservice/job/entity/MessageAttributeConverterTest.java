package net.lminar.jobservice.job.entity;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.SneakyThrows;

/**
 * Unit test for {@link MessageAttributeConverter}.
 *
 * @author Lukas Minar
 */
class MessageAttributeConverterTest {

	private MessageAttributeConverter tested;

	@BeforeEach
	void setup() {
		tested = new MessageAttributeConverter();
	}

	@Test
	void convertToDatabaseColumn() {
		byte[] result = tested.convertToDatabaseColumn(new Message());

		assertNotNull(result);
	}

	@Test
	@SneakyThrows
	void convertToEntityAttribute() {
		ObjectMapper objectMapper = new ObjectMapper();
		byte[] bytes = objectMapper.writeValueAsBytes(new Message());

		Message result = tested.convertToEntityAttribute(bytes);

		assertNotNull(result);
	}
}