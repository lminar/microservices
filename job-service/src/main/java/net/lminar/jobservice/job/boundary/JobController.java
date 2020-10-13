package net.lminar.jobservice.job.boundary;

import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import net.lminar.jobservice.job.entity.Job;
import net.lminar.jobservice.job.entity.Message;

/**
 * Job controller.
 *
 * @author Lukas Minar
 */
@RestController
@RequestMapping("/v1/jobs")
public class JobController {

	@Autowired
	private JobFacade jobFacade;

	/**
	 * Gets job by id.
	 *
	 * @param id job id
	 * @return {@link Job}
	 */
	@GetMapping("/{id}")
	public ResponseEntity<Job> getJob(@PathVariable("id") Long id) {
		Job job = jobFacade.getJob(id);
		return new ResponseEntity<>(job, HttpStatus.OK);
	}

	/**
	 * Creates job.
	 *
	 * @param message message
	 * @param routingKey routing key
	 * @return {@link Job}
	 */
	@PostMapping({"", "/{routingKey}"})
	public ResponseEntity<Job> createJob(@Valid @RequestBody Message message, @PathVariable("routingKey") Optional<String> routingKey) {
		Job job = jobFacade.createJob(message, routingKey.orElse(null));
		return new ResponseEntity<>(job, HttpStatus.CREATED);
	}
}
