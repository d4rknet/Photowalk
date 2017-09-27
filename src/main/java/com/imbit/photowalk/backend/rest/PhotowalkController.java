package com.imbit.photowalk.backend.rest;

import com.imbit.photowalk.backend.domain.entity.Photowalk;
import com.imbit.photowalk.backend.domain.repo.PhotowalkRepository;
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
    public PhotowalkController(PhotowalkRepository photowalkRepository) {this.photowalkRepository = photowalkRepository;
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity addPhotowalk(@RequestBody PhotowalkDto photowalkDto){
        Photowalk photowalk = new Photowalk();
        photowalk.setName(photowalkDto.getName());
        photowalk.setDate(photowalkDto.getDate());
        photowalk.setDescription(photowalkDto.getDescription());
        photowalk.setStartpoint(photowalkDto.getStartpoint());
        photowalk.setEndpoint(photowalkDto.getEndpoint());
        photowalk.setDuration(photowalkDto.getDuration());
        photowalkRepository.save(photowalk);
        return ResponseEntity.created(URI.create("/api/photowalk/" +photowalkDto.getName())).build();
    }




    @RequestMapping
    public List<PhotowalkDto> getPhotowalks(){
        return photowalkRepository.findAll().stream().map(photowalk -> PhotowalkDto.builder().name(photowalk.getName()).build())
                .collect(toList());
    }


   @RequestMapping(path = "/{name}")
    public ResponseEntity<PhotowalkDto> getPhotowalk(@PathVariable String name) {
       Optional<Photowalk> photowalk  = photowalkRepository.findPhotowalkByName(name);
       if (!photowalk.isPresent()) {
           return ResponseEntity.notFound().build();
       } else {
           return ResponseEntity.ok(mapToPhotowalkDto(photowalk.get()));
       }
   }

    private PhotowalkDto mapToPhotowalkDto(Photowalk photowalk){
        return PhotowalkDto.builder()
                .name(photowalk.getName())
                .date(photowalk.getDate())
                .description(photowalk.getDescription())
                .startpoint(photowalk.getStartpoint())
                .endpoint(photowalk.getEndpoint())
                .duration(photowalk.getDuration())
                .build();
    }

}