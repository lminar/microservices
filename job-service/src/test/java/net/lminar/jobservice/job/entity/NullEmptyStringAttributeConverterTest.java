package net.lminar.jobservice.job.entity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.apache.commons.lang.StringUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Unit test for {@link NullEmptyStringAttributeConverter}.
 *
 * @author Lukas Minar
 */
class NullEmptyStringAttributeConverterTest {

	private NullEmptyStringAttributeConverter tested;

	@BeforeEach
	void setup() {
		tested = new NullEmptyStringAttributeConverter();
	}

	@Test
	void convertToDatabaseColumn_null() {
		String result = tested.convertToDatabaseColumn(null);

		assertNull(result);
	}

	@Test
	void convertToDatabaseColumn_empty() {
		String result = tested.convertToDatabaseColumn(StringUtils.EMPTY);

		assertNull(result);
	}

	@Test
	void convertToDatabaseColumn_whitespace() {
		String result = tested.convertToDatabaseColumn("  ");

		assertNull(result);
	}

	@Test
	void convertToDatabaseColumn_value() {
		String result = tested.convertToDatabaseColumn("routingKey");

		assertEquals("routingKey", result);
	}

	@Test
	void convertToEntityAttribute_null() {
		String result = tested.convertToEntityAttribute(null);

		assertEquals(StringUtils.EMPTY, result);
	}

	@Test
	void convertToEntityAttribute_empty() {
		String result = tested.convertToEntityAttribute(StringUtils.EMPTY);

		assertEquals(StringUtils.EMPTY, result);
	}

	@Test
	void convertToEntityAttribute_value() {
		String result = tested.convertToEntityAttribute("routingKey");

		assertEquals("routingKey", result);
	}
}