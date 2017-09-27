package com.imbit.photowalk.backend.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Role {

	@Id
//	@Column(name = "USER_ID")
//	@GeneratedValue(strategy = GenerationType.AUTO)
//	private Integer userId;

	private String username;

	//@Column(unique = true,nullable = false)
	private String name;
}
