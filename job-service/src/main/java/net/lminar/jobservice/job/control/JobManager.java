package net.lminar.jobservice.job.control;

import static net.lminar.jobservice.job.entity.JobStatus.NEW;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;
import net.lminar.jobservice.job.entity.Job;
import net.lminar.jobservice.job.entity.JobStatus;
import net.lminar.jobservice.job.entity.Message;

/**
 * Job manager.
 *
 * @author Lukas Minar
 */
@Slf4j
@Component
public class JobManager {

	@Autowired
	private JobRepository jobRepository;

	/**
	 * Gets jobs for status.
	 *
	 * @param status job status
	 * @return {@link List} of {@link Job}
	 */
	public List<Job> getJobs(JobStatus status) {
		return jobRepository.findAllByStatus(status);
	}

	/**
	 * Updates job status.
	 *
	 * @param id job id
	 * @param status job status
	 */
	public void updateStatus(Long id, JobStatus status) {
		Optional<Job> job = jobRepository.findById(id);

		if (job.isEmpty()) {
			throw new IllegalStateException(String.format("Job [%s] not found.", id));
		}

		Job jobToUpdate = job.get();
		jobToUpdate.setStatus(status);

		jobRepository.save(jobToUpdate);
	}

	/**
	 * Creates job.
	 *
	 * @param message message
	 * @param routingKey routing key
	 * @return {@link Job}
	 */
	public Job createJob(final Message message, final String routingKey) {
		Job job = new Job();
		job.setStatus(NEW);
		job.setRoutingKey(routingKey);
		job.setMessage(message);

		return jobRepository.save(job);
	}

	/**
	 * Gets job.
	 *
	 * @param id job id
	 * @return {@link Job}
	 */
	public Job getJob(final Long id) {
		Optional<Job> job = jobRepository.findById(id);

		if (job.isEmpty()) {
			log.error("Job [{}] not found.", id);
			throw new EntityNotFoundException(String.format("Job [%s] not found.", id));
		}

		return job.get();
	}
}
