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

	//@Column(nullable = false)
	private String lastname;

	//@Column(nullable = false)
	private String emailaddress;

	@Column(nullable = false)
	private String password;

/*	@Column(nullable = false)
	private String salt;
*/
	@ManyToOne
	private Role role;
}
