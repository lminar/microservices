package net.lminar.jobservice.job.boundary;

import static net.lminar.jobservice.job.entity.JobStatus.NEW;
import static org.springframework.transaction.annotation.Propagation.REQUIRES_NEW;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import lombok.extern.slf4j.Slf4j;
import net.lminar.jobservice.job.control.JobManager;
import net.lminar.jobservice.job.entity.Job;
import net.lminar.jobservice.job.entity.JobStatus;
import net.lminar.jobservice.job.entity.Message;

/**
 * Job facade.
 *
 * @author Lukas Minar
 */
@Slf4j
@Component
public class JobFacade {

	@Autowired
	private JobManager jobManager;

	/**
	 * Gets jobs with status {@link JobStatus#NEW}.
	 *
	 * @return {@link List} of {@link Job}
	 */
	@Transactional(readOnly = true)
	public List<Job> getNewJobs() {
		return jobManager.getJobs(NEW);
	}

	/**
	 * Updates job status.
	 *
	 * @param id job id
	 * @param status status to set
	 */
	@Transactional(propagation = REQUIRES_NEW)
	public void updateStatus(Long id, JobStatus status) {
		log.debug("Update job [{}] status to [{}].", id, status);
		jobManager.updateStatus(id, status);
	}

	/**
	 * Creates job.
	 *
	 * @param message message
	 * @param routingKey routing key
	 * @return {@link Job}
	 */
	@Transactional
	Job createJob(Message message, String routingKey) {
		log.debug("Create job message [{}], routing key [{}]", message.getId(), routingKey);
		return jobManager.createJob(message, routingKey);
	}

	/**
	 * Gets job.
	 *
	 * @param id job id
	 * @return {@link Job}
	 */
	@Transactional(readOnly = true)
	Job getJob(Long id) {
		log.debug("Get job [{}]", id);
		return jobManager.getJob(id);
	}
}
