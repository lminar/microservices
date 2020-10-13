package net.lminar.jobservice.cron.control;

import static java.util.Collections.emptyList;
import static java.util.Collections.singletonList;
import static net.lminar.jobservice.job.entity.JobStatus.ERROR;
import static net.lminar.jobservice.job.entity.JobStatus.IN_PROGRESS;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import net.lminar.jobservice.job.boundary.JobFacade;
import net.lminar.jobservice.job.entity.Job;
import net.lminar.jobservice.job.entity.JobStatus;
import net.lminar.jobservice.job.entity.Message;
import net.lminar.jobservice.message.boundary.MessageFacade;

/**
 * Unit test for {@link JobScheduler}.
 *
 * @author Lukas Minar
 */
@ExtendWith(MockitoExtension.class)
class JobSchedulerTest {

	@Mock
	private JobFacade jobFacade;

	@Mock
	private MessageFacade messageFacade;

	@InjectMocks
	private JobScheduler tested;

	@Test
	void schedule_ok() {
		Message message = mock(Message.class);

		Job job = mock(Job.class);
		when(job.getId()).thenReturn(1L);
		when(job.getRoutingKey()).thenReturn("routingKey");
		when(job.getMessage()).thenReturn(message);

		when(jobFacade.getNewJobs()).thenReturn(singletonList(job));
		when(messageFacade.sendMessage(message, "routingKey")).thenReturn(true);

		tested.schedule();

		verify(messageFacade).sendMessage(message, "routingKey");
		verify(jobFacade).updateStatus(1L, IN_PROGRESS);
	}

	@Test
	void schedule_jobsNotFound() {
		when(jobFacade.getNewJobs()).thenReturn(emptyList());

		tested.schedule();

		verify(messageFacade, never()).sendMessage(any(Message.class), anyString());
		verify(jobFacade, never()).updateStatus(anyLong(), any(JobStatus.class));
	}

	@Test
	void schedule_sendFailed() {
		Message message = mock(Message.class);

		Job job = mock(Job.class);
		when(job.getId()).thenReturn(1L);
		when(job.getRoutingKey()).thenReturn("routingKey");
		when(job.getMessage()).thenReturn(message);

		when(jobFacade.getNewJobs()).thenReturn(singletonList(job));
		when(messageFacade.sendMessage(message, "routingKey")).thenReturn(false);

		tested.schedule();

		verify(messageFacade).sendMessage(message, "routingKey");
		verify(jobFacade).updateStatus(1L, ERROR);
	}
}