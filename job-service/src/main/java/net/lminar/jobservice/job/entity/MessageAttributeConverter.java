package net.lminar.jobservice.job.entity;

import java.io.IOException;

import javax.persistence.AttributeConverter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;

/**
 * Attribute converter for Message <-> byte[].
 *
 * @author Lukas Minar
 */
@Slf4j
public class MessageAttributeConverter implements AttributeConverter<Message, byte[]> {

	private ObjectMapper objectMapper = new ObjectMapper();

	@Override
	public byte[] convertToDatabaseColumn(Message message) {
		try {
			return objectMapper.writeValueAsBytes(message);
		} catch (final JsonProcessingException e) {
			log.error("Write message as byte[] failed.", e);
			return null;
		}
	}

	@Override
	public Message convertToEntityAttribute(byte[] bytes) {
		try {
			return objectMapper.readValue(bytes, Message.class);
		} catch (final IOException e) {
			log.error("Read byte[] as message failed.", e);
			return null;
		}
	}
}
