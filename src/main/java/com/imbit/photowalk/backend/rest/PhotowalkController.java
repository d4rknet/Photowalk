package com.imbit.photowalk.backend.rest;

import com.imbit.photowalk.backend.domain.entity.Photowalk;
import com.imbit.photowalk.backend.domain.entity.User;
import com.imbit.photowalk.backend.domain.repo.PhotowalkRepository;
import com.imbit.photowalk.backend.domain.repo.UserRepository;
import com.imbit.photowalk.backend.dto.UserDto;
import com.imbit.photowalk.backend.dto.PhotowalkDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import static java.util.stream.Collectors.toList;

@RestController
@RequestMapping("/api/photowalk")
public class PhotowalkController {

    private final PhotowalkRepository photowalkRepository;

    @Autowired
    public PhotowalkController(PhotowalkRepository PhotowalkRepository) {
        this.photowalkRepository = PhotowalkRepository;
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity addPhotowalk(@RequestBody PhotowalkDto photowalkDto){
        Photowalk photowalk = new Photowalk();
        photowalk.setName(photowalkDto.getName());
        photowalk.setDate(photowalk.getDate());
        photowalk.setDescription(photowalkDto.getDescription());
        photowalk.setStartpoint(photowalkDto.getStartpoint());
        photowalk.setEndpoint(photowalk.getEndpoint());
        photowalk.setDuration(photowalk.getDuration());
        photowalkRepository.save(photowalk);
        return ResponseEntity.created(URI.create("/api/photowalk/"+photowalkDto.getName())).build();
    }

    private PhotowalkDto mapToPhotowalkDto(Photowalk photowalk){
        return PhotowalkDto.builder()
                .photowalkId(photowalk.getPhotowalkId())
                .name(photowalk.getName())
                .date(photowalk.getDate())
                .description(photowalk.getDescription())
                .startpoint(photowalk.getStartpoint())
                .endpoint(photowalk.getEndpoint())
                .duration(photowalk.getDuration())
                .build();
    }

}