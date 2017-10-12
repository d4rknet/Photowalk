package com.imbit.photowalk.backend.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.imbit.photowalk.backend.domain.entity.Route;
import com.imbit.photowalk.backend.domain.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonInclude(NON_NULL)
public class PhotowalkDto {
	private Integer photowalkId;
	private String name;
	private Integer duration;
	//private String datum;
	private String description;
	private String startpoint;
	private String endpoint;
	private Route route;
	private User owner;
}
