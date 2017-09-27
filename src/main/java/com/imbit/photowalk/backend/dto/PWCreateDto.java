package com.imbit.photowalk.backend.dto;

import com.imbit.photowalk.backend.domain.entity.Route;
import com.imbit.photowalk.backend.domain.entity.User;
import lombok.Data;

import java.util.Date;

@Data
public class PWCreateDto {
	private Integer photowalkId;
	private String name;
	private Integer duration;
	//private Date date;
	private String description;
	private String startpoint;
	private String endpoint;
	private Route route;
	private User owner;
}