package com.imbit.photowalk.backend.domain.entity;

import com.fasterxml.jackson.annotation.JsonView;
import com.imbit.photowalk.backend.rest.View.PhotowalkDetailed;
import com.imbit.photowalk.backend.rest.View.UserDetailed;
import lombok.Data;

import javax.persistence.*;

import static java.lang.Boolean.TRUE;

@Entity
@Data
public class User {
	@Id
	@Column(name = "USER_ID")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer userId;

	@JsonView({UserDetailed.class, PhotowalkDetailed.class})
	@Column(unique = true,nullable = false)
	private String username;

	@JsonView({UserDetailed.class, PhotowalkDetailed.class})
	@Column(nullable = false)
	private String firstname;

	@Column(nullable = false)
	@JsonView({UserDetailed.class, PhotowalkDetailed.class})
	private String lastname;

	@Column(nullable = false)
	@JsonView(UserDetailed.class)
	private String emailaddress;

	@Column(nullable = false)
	@JsonView(UserDetailed.class)
	private String password;

	@Column(nullable = false )
	private Boolean enabled = TRUE;

	@JsonView({UserDetailed.class, PhotowalkDetailed.class})
	@ManyToOne
	private Role role;
}
