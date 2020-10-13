package net.lminar.jobservice.job.entity;

import java.time.LocalDateTime;

import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import lombok.Data;
import lombok.ToString;

/**
 * Job entity.
 *
 * @author Lukas Minar
 */
@Data
@Entity
public class Job {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Convert(converter = NullEmptyStringAttributeConverter.class)
	private String routingKey;

	@NotNull
	@ToString.Exclude
	@Convert(converter = MessageAttributeConverter.class)
	private Message message;

	@NotNull
	@Enumerated(EnumType.STRING)
	private JobStatus status;

	@CreationTimestamp
	private LocalDateTime created;

	@UpdateTimestamp
	private LocalDateTime lastUpdated;
}
