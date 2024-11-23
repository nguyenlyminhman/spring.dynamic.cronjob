package com.cronjob.models;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CronJobModel {
    private long id;
    private String code;
    private String pattern;
    private String description;
}
