package com.cronjob.controllers;

import com.cronjob.entities.CronJobEntity;
import com.cronjob.services.CronJobService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("api/cronjob")
public class CronJobController {

    @Autowired
    private CronJobService cronJobService;

    @GetMapping("/get-all")
    public List<CronJobEntity> getAll(){
        List<CronJobEntity> rs = null;
        try {
            rs = cronJobService.findAll();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return rs;
    }

    @PostMapping("/start")
    public CronJobEntity start(@RequestBody HashMap hashMapBody){
        CronJobEntity rs = null;
        String cronCode = String.valueOf(hashMapBody.getOrDefault("job", ""));
        try {
            rs = cronJobService.findByCode(cronCode);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return rs;
    }
}
