package com.cronjob.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
@AllArgsConstructor
public class CronJobModel {
    private long id;
    private String code;
    private String pattern;
    private String description;
}
