package com.imbit.photowalk.backend.rest;

import com.fasterxml.jackson.annotation.JsonView;
import com.imbit.photowalk.backend.domain.entity.Photowalk;
import com.imbit.photowalk.backend.domain.entity.User;
import com.imbit.photowalk.backend.domain.repo.PhotowalkRepository;
import com.imbit.photowalk.backend.domain.repo.UserRepository;
import com.imbit.photowalk.backend.dto.PhotowalkDto;
import com.imbit.photowalk.backend.rest.View.PhotowalkDetailed;
import com.imbit.photowalk.backend.rest.View.PhotowalkSummary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/photowalk")
public class PhotowalkController {

	private final PhotowalkRepository photowalkRepository;
	private final UserRepository userRepository;

	@Autowired
	public PhotowalkController(PhotowalkRepository photowalkRepository,
							   UserRepository userRepository) {
		this.photowalkRepository = photowalkRepository;
		this.userRepository = userRepository;
	}

	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity addPhotowalk(@JsonView(PhotowalkSummary.class) @RequestBody Photowalk photowalk,
									   Authentication authentication) {
		User user = userRepository.findUserByUsername(authentication.getName()).get();
		photowalk.setOwner(user);
		photowalkRepository.save(photowalk);
		return ResponseEntity.created(URI.create("/api/photowalk/" + photowalk.getName())).build();
	}

	@JsonView(PhotowalkSummary.class)
	@RequestMapping
	public List<Photowalk> getPhotowalks() {
		return photowalkRepository.findAll();
	}

	@JsonView(PhotowalkDetailed.class)
	@RequestMapping(path = "/{name}")
	public ResponseEntity<Photowalk> getPhotowalk(@PathVariable String name) {
		Optional<Photowalk> photowalk = photowalkRepository.findPhotowalkByName(name);
		if (!photowalk.isPresent()) {
			return ResponseEntity.notFound().build();
		} else {
			return ResponseEntity.ok(photowalk.get());
		}
	}

	private PhotowalkDto mapToPhotowalkDto(Photowalk photowalk) {
		return PhotowalkDto.builder()
				.name(photowalk.getName())
				// .date(photowalk.getDate())
				.description(photowalk.getDescription())
				.startpoint(photowalk.getStartpoint())
				.endpoint(photowalk.getEndpoint())
				.duration(photowalk.getDuration())
				.build();
	}
}