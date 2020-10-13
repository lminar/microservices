package net.lminar.jobservice.job.entity;

import javax.persistence.AttributeConverter;

import org.apache.commons.lang.StringUtils;

/**
 * Attribute converter for null <-> empty string.
 *
 * @author Lukas Minar
 */
public class NullEmptyStringAttributeConverter implements AttributeConverter<String, String> {

	@Override
	public String convertToDatabaseColumn(final String value) {
		return StringUtils.isBlank(value) ? null : value;
	}

	@Override
	public String convertToEntityAttribute(final String value) {
		return value == null ? StringUtils.EMPTY : value;
	}
}
