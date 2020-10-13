package net.lminar.jobservice.job.control;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.Repository;

import net.lminar.jobservice.job.entity.Job;
import net.lminar.jobservice.job.entity.JobStatus;

/**
 * Job repository.
 *
 * @author Lukas Minar
 */
public interface JobRepository extends Repository<Job, Long> {

	List<Job> findAllByStatus(JobStatus status);

	Optional<Job> findById(Long id);

	Job save(Job job);
}
