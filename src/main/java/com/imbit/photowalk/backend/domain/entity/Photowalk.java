package com.imbit.photowalk.backend.domain.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

public class Photowalk {
    @Id
    @Column(name = "Photowalk_ID")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer PhotowalkId;

    @Column(nullable=false)
    private Date date;

    @Column(nullable = false)
    private String Description;

    @Column(nullable = false)
    private String Startpoint;

    @Column(nullable = false)
    private String Endpoint;

    @Column(nullable = false)
    private Integer Duration;

    @OneToOne
    private Route route;

    @ManyToOne
    private User user;
}
