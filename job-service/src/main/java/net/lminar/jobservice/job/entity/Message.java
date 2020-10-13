package net.lminar.jobservice.job.entity;

import java.io.Serializable;
import java.util.Map;

import javax.validation.constraints.NotNull;

import lombok.Data;

/**
 * Message entity.
 *
 * @author Lukas Minar
 */
@Data
public class Message implements Serializable {

	@NotNull
	private String id;

	@NotNull
	private Map<String, Object> data;
}
