package com.imbit.photowalk.backend.domain.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Data
public class Photowalk {
    @Id
    @Column(name = "Photowalk_ID")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer photowalkId;

   @Column(unique = true, nullable = false)
    private String name;

    //@Column(nullable=false)
  //  private Date date;

   // @Column(nullable = false)
    private String description;

 //   @Column(nullable = false)
    private String startpoint;

  //  @Column(nullable = false)
    private String endpoint;

    //@Column(nullable = false)
    private Integer duration;

    @OneToOne
    private Route route;

    @ManyToOne
    private User owner;
    
    @ManyToMany
    @JoinTable(name = "Participant")
    private List<User> participants;
}
