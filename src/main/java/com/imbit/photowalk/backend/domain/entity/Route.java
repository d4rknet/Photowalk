package com.imbit.photowalk.backend.domain.entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class Route {
    @Id
    @Column(name = "ROUTE_ID")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer routeID;

    @Column(nullable=false)
    private float latidute;

    @Column(nullable=false)
    private float longtidue;

    @Column(nullable=false)
    private integer position;

