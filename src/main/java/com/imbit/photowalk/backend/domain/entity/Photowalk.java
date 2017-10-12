package com.imbit.photowalk.backend.domain.entity;

import com.fasterxml.jackson.annotation.JsonView;
import com.imbit.photowalk.backend.rest.View.PhotowalkDetailed;
import com.imbit.photowalk.backend.rest.View.PhotowalkSummary;
import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
public class Photowalk {
	@Id
	@Column(name = "Photowalk_ID")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer photowalkId;

	@Column(unique = true, nullable = false, length = 70)
	@JsonView({PhotowalkDetailed.class, PhotowalkSummary.class})
	private String name;

	//@Column
	//private String datum;

	// @Column(nullable = false)
	@JsonView({PhotowalkDetailed.class, PhotowalkSummary.class})
	private String description;

	@Column(nullable = false, length = 90)
	@JsonView({PhotowalkDetailed.class, PhotowalkSummary.class})
	private String startpoint;

	@Column(nullable = false, length = 90)
	@JsonView({PhotowalkDetailed.class, PhotowalkSummary.class})
	private String endpoint;

	//@Column(nullable = false)
	@JsonView({PhotowalkDetailed.class, PhotowalkSummary.class})
	private Integer duration;

	@OneToOne
	@JsonView(PhotowalkDetailed.class)
	private Route route;

	@ManyToOne
	@JsonView(PhotowalkDetailed.class)
	private User owner;

	@ManyToMany
	@JoinTable(name = "Applicants")
	@JsonView(PhotowalkDetailed.class)
	private List<User> applicants;

	@ManyToMany
	@JoinTable(name = "Participants")
	@JsonView(PhotowalkDetailed.class)
	private List<User> participants;
}
