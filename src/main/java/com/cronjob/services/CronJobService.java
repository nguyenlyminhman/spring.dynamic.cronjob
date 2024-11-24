package com.cronjob.services;

import com.cronjob.entities.CronJobEntity;

import com.cronjob.models.CronJobModel;
import com.cronjob.mvc.AbstractService;
import com.cronjob.repositories.CronJobRepository;
import com.cronjob.schedule.FirstTaskSchedule;
import com.cronjob.schedule.SecondTaskSchedule;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CronJobService extends AbstractService<CronJobEntity, Long> {

    @Autowired
    private final CronJobRepository cronJobRepository;

    @Autowired
    private final FirstTaskSchedule firstTaskSchedule;

    @Autowired
    private final SecondTaskSchedule secondTaskSchedule;

    public CronJobService(CronJobRepository cronJobRepository,
                          FirstTaskSchedule firstTaskSchedule,
                          SecondTaskSchedule secondTaskSchedule) {
        super(cronJobRepository);
        this.cronJobRepository = cronJobRepository;
        this.firstTaskSchedule = firstTaskSchedule;
        this.secondTaskSchedule = secondTaskSchedule;
    }

    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public List<CronJobEntity> findAll() {
        return cronJobRepository.getAllByQuery();
    }

    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public CronJobEntity findByCode(String cronCode) {
        return cronJobRepository.getCronJobByCode(cronCode);
    }

    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public CronJobEntity start(String cronCode) {
        CronJobEntity cronJob = null;
        try {
            cronJob = cronJobRepository.getCronJobByCode(cronCode);
            ModelMapper modelMapper = new ModelMapper();
            CronJobModel userDTO = modelMapper.map(cronJob, CronJobModel.class);
            if ("SCHEDULE_01".equalsIgnoreCase(userDTO.getCode()))
                firstTaskSchedule.scheduleTaskWithCronExpression(userDTO.getPattern(), userDTO.getCode());
            if ("SCHEDULE_02".equalsIgnoreCase(userDTO.getCode()))
                secondTaskSchedule.scheduleTaskWithCronExpression(userDTO.getPattern(), userDTO.getCode());

        } catch (Exception e) {
            e.printStackTrace();
        }
        return cronJob;
    }

    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public List<CronJobEntity> startAll() {
        List<CronJobEntity> cronJobList = null;
        try {
            cronJobList = cronJobRepository.getAllByQuery();
            cronJobList.forEach( cronJobEntity -> {
                ModelMapper modelMapper = new ModelMapper();
                CronJobModel userDTO = modelMapper.map(cronJobEntity, CronJobModel.class);
                if ("SCHEDULE_01".equalsIgnoreCase(userDTO.getCode()))
                    firstTaskSchedule.scheduleTaskWithCronExpression(userDTO.getPattern(), userDTO.getCode());
                if ("SCHEDULE_02".equalsIgnoreCase(userDTO.getCode()))
                    secondTaskSchedule.scheduleTaskWithCronExpression(userDTO.getPattern(), userDTO.getCode());
            } );
        } catch (Exception e) {
            e.printStackTrace();
        }
        return cronJobList;
    }

    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public String stopAll() {
        String rs = "All Stopped";
        try {
            firstTaskSchedule.stopScheduledTask();
            secondTaskSchedule.stopScheduledTask();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return rs;
    }

    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public CronJobEntity stop(String cronCode) {
        CronJobEntity cronJob = null;
        try {
            cronJob = cronJobRepository.getCronJobByCode(cronCode);
            if ("SCHEDULE_01".equalsIgnoreCase(cronCode))
                firstTaskSchedule.stopScheduledTask();
            if ("SCHEDULE_02".equalsIgnoreCase(cronCode))
                secondTaskSchedule.stopScheduledTask();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return cronJob;
    }
}
