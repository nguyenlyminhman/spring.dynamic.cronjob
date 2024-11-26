package com.cronjob.controllers;

import com.cronjob.entities.CronJobEntity;
import com.cronjob.services.CronJobThreadPoolService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("api/cronjob/thread-pool")
public class CronJobThreadPoolController {
    @Autowired
    private CronJobThreadPoolService cronJobService;


    @PostMapping("/start-all")
    public List<CronJobEntity> startAll(){
        List<CronJobEntity> rs = null;
        try {
            rs = cronJobService.startAll();
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
            rs = cronJobService.start(cronCode);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return rs;
    }

    @PostMapping("/stop-all")
    public String stopAll(){
        String rs = null;
        try {
            rs = cronJobService.stopAll();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return rs;
    }

    @PostMapping("/stop")
    public CronJobEntity stop(@RequestBody HashMap hashMapBody){
        CronJobEntity rs = null;
        String cronCode = String.valueOf(hashMapBody.getOrDefault("job", ""));
        try {
            rs = cronJobService.stop(cronCode);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return rs;
    }
}
