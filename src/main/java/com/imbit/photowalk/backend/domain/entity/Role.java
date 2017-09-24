package com.imbit.photowalk.backend.domain.entity;

import javax.persistence.*;

@Entity
public class Role {

	@Id
	@Column(name = "USER_ID")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer userId;

	@Column(unique = true,nullable = false)
	private String name;



}
