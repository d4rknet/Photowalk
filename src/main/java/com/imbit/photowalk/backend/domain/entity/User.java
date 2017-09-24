package com.imbit.photowalk.backend.domain.entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class User {
	@Id
	@Column(name = "USER_ID")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer userId;

	@Column(unique = true,nullable = false)
	private String username;

	@Column(nullable = false)
	private String firstname;

	private String password;

	@ManyToOne
	private Role role;


}
