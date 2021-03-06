package com.imbit.photowalk.backend.controller.rest;

import com.fasterxml.jackson.annotation.JsonView;
import com.imbit.photowalk.backend.controller.rest.View.PhotowalkDetailed;
import com.imbit.photowalk.backend.controller.rest.View.PhotowalkSummary;
import com.imbit.photowalk.backend.domain.entity.Photowalk;
import com.imbit.photowalk.backend.domain.entity.User;
import com.imbit.photowalk.backend.domain.repo.PhotowalkRepository;
import com.imbit.photowalk.backend.domain.repo.UserRepository;
import com.imbit.photowalk.backend.security.Authenticated;
import com.imbit.photowalk.backend.security.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@RestController
@RequestMapping("/api/photowalks")
public class PhotowalkController {

	private final PhotowalkRepository photowalkRepository;
	private final UserRepository userRepository;
	private final AuthenticationService authenticationService;


	@Autowired
	public PhotowalkController(PhotowalkRepository photowalkRepository,
							   UserRepository userRepository,
							   AuthenticationService authenticationService) {
		this.photowalkRepository = photowalkRepository;
		this.userRepository = userRepository;
		this.authenticationService = authenticationService;
	}

	@Authenticated
	@RequestMapping(method = POST)
	public ResponseEntity addPhotowalk(@JsonView(PhotowalkSummary.class) @RequestBody Photowalk photowalk) {
		photowalk.setOwner(authenticationService.getCurrentUser());
		photowalkRepository.save(photowalk);
		return ResponseEntity.created(URI.create("/api/photowalks/" + photowalk.getName())).build();
	}

	@Authenticated
	@JsonView(PhotowalkSummary.class)
	@RequestMapping(method = GET)
	public List<Photowalk> getPhotowalks() {
		return photowalkRepository.findAll();
	}

	@Authenticated
	@JsonView(PhotowalkDetailed.class)
	@RequestMapping(path = "/{name}", method = GET)
	public ResponseEntity<Photowalk> getPhotowalk(@PathVariable String name) {
		return getPhotowalkResponse(ph -> ph, name);
	}

	@Authenticated
	@JsonView(PhotowalkDetailed.class)
	@RequestMapping(path = "/{name}/applicants", method = GET)
	public ResponseEntity<List<User>> getApplicants(@PathVariable String name) {
		return getPhotowalkResponse(Photowalk::getApplicants, name);
	}

	@Authenticated
	@JsonView(PhotowalkDetailed.class)
	@RequestMapping(path = "/{name}/applicants", method = POST)
	public ResponseEntity addApplicant(@PathVariable String name) {
		Optional<Photowalk> photowalk = photowalkRepository.findPhotowalkByName(name);
		photowalk.get().getApplicants().add(authenticationService.getCurrentUser());
		return ResponseEntity.ok().build();

	}

	@Authenticated
	@JsonView(PhotowalkDetailed.class)
	@RequestMapping(path = "/{name}/participants", method = GET)
	public ResponseEntity<List<User>> participants(@PathVariable String name) {
		return getPhotowalkResponse(Photowalk::getParticipants, name);
	}

	@Authenticated
	@JsonView(PhotowalkDetailed.class)
	@RequestMapping(path = "/{name}/participants", method = POST)
	public ResponseEntity addParticipants(@PathVariable String name) {
		Optional<Photowalk> photowalk = photowalkRepository.findPhotowalkByName(name);

		photowalk.get().getApplicants().add(authenticationService.getCurrentUser());

		return ResponseEntity.ok().build();
	}

	private <T> ResponseEntity<T> getPhotowalkResponse(Function<Photowalk, T> extract, String name) {
		Optional<Photowalk> photowalk = photowalkRepository.findPhotowalkByName(name);
		if (!photowalk.isPresent()) {
			return ResponseEntity.notFound().build();
		} else {
			return ResponseEntity.ok(extract.apply(photowalk.get()));
		}
	}
}