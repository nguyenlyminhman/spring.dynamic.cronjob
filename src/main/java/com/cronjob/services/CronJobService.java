package com.cronjob.services;

import com.cronjob.entities.CronJobEntity;

import com.cronjob.models.CronJobModel;
import com.cronjob.mvc.AbstractService;
import com.cronjob.repositories.CronJobRepository;
import com.cronjob.schedule.DynamicOneTaskScheduler;
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
    private final DynamicOneTaskScheduler dynamicTaskScheduler;

    public CronJobService(CronJobRepository cronJobRepository, DynamicOneTaskScheduler dynamicTaskScheduler) {
        super(cronJobRepository);
        this.cronJobRepository = cronJobRepository;
        this.dynamicTaskScheduler = dynamicTaskScheduler;
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
            dynamicTaskScheduler.scheduleTaskWithCronExpression(userDTO.getPattern(), userDTO.getCode());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return cronJob;
    }

    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public CronJobEntity stop(String cronCode) {
        CronJobEntity cronJob = null;
        try {
            cronJob = cronJobRepository.getCronJobByCode(cronCode);
            dynamicTaskScheduler.stopScheduledTask();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return cronJob;
    }
}
