package com.cronjob.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

import java.io.Serializable;

@Entity
@Table(name = "cronjob")
@Data
public class CronJobEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "_id")
    private long id;

    @Column(name = "code")
    private String code;

    @Column(name = "pattern")
    private String pattern;

    @Column(name = "description")
    private String description;
}
