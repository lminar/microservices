package net.lminar.jobservice.cron.control;

import static net.lminar.jobservice.job.entity.JobStatus.ERROR;
import static net.lminar.jobservice.job.entity.JobStatus.IN_PROGRESS;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;
import net.lminar.jobservice.job.boundary.JobFacade;
import net.lminar.jobservice.job.entity.Job;
import net.lminar.jobservice.job.entity.JobStatus;
import net.lminar.jobservice.message.boundary.MessageFacade;

/**
 * @author Lukas Minar
 */
@Slf4j
@Component
public class JobScheduler {

	@Autowired
	private JobFacade jobFacade;

	@Autowired
	private MessageFacade messageFacade;

	/**
	 * Gets jobs with status {@link JobStatus#NEW} send job for processing and update job status to {@link JobStatus#IN_PROGRESS} or {@link JobStatus#ERROR}
	 */
	@Scheduled(cron = "${job.scheduler.cron:-}")
	public void schedule() {
		List<Job> jobs = jobFacade.getNewJobs();

		log.trace("Found [{}] jobs for processing.", jobs.size());

		jobs.forEach(job -> {
			boolean send = messageFacade.sendMessage(job.getMessage(), job.getRoutingKey());

			JobStatus status = send ? IN_PROGRESS : ERROR;
			jobFacade.updateStatus(job.getId(), status);
		});
	}
}
