package net.lminar.jobservice.job.control;

import static net.lminar.jobservice.job.entity.JobStatus.ERROR;
import static net.lminar.jobservice.job.entity.JobStatus.NEW;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import net.lminar.jobservice.job.entity.Job;
import net.lminar.jobservice.job.entity.Message;

/**
 * Unit test for {@link JobManager}.
 *
 * @author Lukas Minar
 */
@ExtendWith(MockitoExtension.class)
class JobManagerTest {

	@Mock
	private JobRepository jobRepository;

	@InjectMocks
	private JobManager tested;

	@Test
	void getJobs() {
		List<Job> jobs = new ArrayList<>();
		when(jobRepository.findAllByStatus(NEW)).thenReturn(jobs);

		List<Job> result = tested.getJobs(NEW);

		assertEquals(jobs, result);
	}

	@Test
	void updateStatus_ok() {
		Job job = mock(Job.class);
		when(jobRepository.findById(1L)).thenReturn(Optional.of(job));

		tested.updateStatus(1L, ERROR);

		verify(job).setStatus(ERROR);
		verify(jobRepository).save(job);
	}

	@Test
	void updateStatus_jobNotFound() {
		when(jobRepository.findById(1L)).thenReturn(Optional.empty());

		assertThrows(IllegalStateException.class, () -> tested.updateStatus(1L, ERROR));
	}

	@Test
	void createJob() {
		Message message = mock(Message.class);

		tested.createJob(message, "routingKey");

		ArgumentCaptor<Job> captor = ArgumentCaptor.forClass(Job.class);
		verify(jobRepository).save(captor.capture());

		Job job = captor.getValue();
		assertEquals(NEW, job.getStatus());
		assertEquals(message, job.getMessage());
		assertEquals("routingKey", job.getRoutingKey());
	}

	@Test
	void getJob_ok() {
		Job job = mock(Job.class);
		when(jobRepository.findById(1L)).thenReturn(Optional.of(job));

		Job result = tested.getJob(1L);

		assertEquals(job, result);
	}

	@Test
	void getJob_jobNotFound() {
		when(jobRepository.findById(1L)).thenReturn(Optional.empty());

		assertThrows(EntityNotFoundException.class, () -> tested.getJob(1L));
	}
}