package net.lminar.jobservice.job.boundary;

import static net.lminar.jobservice.job.entity.JobStatus.IN_PROGRESS;
import static net.lminar.jobservice.job.entity.JobStatus.NEW;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import net.lminar.jobservice.job.control.JobManager;
import net.lminar.jobservice.job.entity.Job;
import net.lminar.jobservice.job.entity.Message;

/**
 * Unit test for {@link JobFacade}.
 *
 * @author Lukas Minar
 */
@ExtendWith(MockitoExtension.class)
class JobFacadeTest {

	@Mock
	private JobManager jobManager;

	@InjectMocks
	private JobFacade tested;

	@Test
	void getNewJobs() {
		List<Job> jobs = new ArrayList<>();
		when(jobManager.getJobs(NEW)).thenReturn(jobs);

		List<Job> result = tested.getNewJobs();

		assertEquals(jobs, result);
	}

	@Test
	void updateStatus() {
		tested.updateStatus(1L, IN_PROGRESS);

		verify(jobManager).updateStatus(1L, IN_PROGRESS);
	}

	@Test
	void createJob() {
		Message message = mock(Message.class);

		tested.createJob(message, "routingKey");

		verify(jobManager).createJob(message, "routingKey");
	}

	@Test
	void getJob() {
		Job job = mock(Job.class);
		when(jobManager.getJob(1L)).thenReturn(job);

		Job result = tested.getJob(1L);

		assertEquals(job, result);
	}
}