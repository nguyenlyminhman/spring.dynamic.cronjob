package com.cronjob.services;

import com.cronjob.entities.CronJobEntity;
import com.cronjob.models.CronJobModel;
import com.cronjob.mvc.AbstractService;
import com.cronjob.repositories.CronJobRepository;
import com.cronjob.scheduleThreadPool.FourthTaskSchedule;
import com.cronjob.scheduleThreadPool.ThirdTaskSchedule;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CronJobThreadPoolService extends AbstractService<CronJobEntity, Long> {
    @Autowired
    private final CronJobRepository cronJobRepository;

    @Autowired
    private final ThirdTaskSchedule thirdTaskSchedule;

    @Autowired
    private final FourthTaskSchedule fourthTaskSchedule;

    public CronJobThreadPoolService(CronJobRepository cronJobRepository,
                                    ThirdTaskSchedule thirdTaskSchedule,
                                    FourthTaskSchedule fourthTaskSchedule) {
        super(cronJobRepository);
        this.cronJobRepository = cronJobRepository;
        this.thirdTaskSchedule = thirdTaskSchedule;
        this.fourthTaskSchedule = fourthTaskSchedule;
    }

    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public CronJobEntity start(String cronCode) {
        CronJobEntity cronJob = null;
        try {
            cronJob = cronJobRepository.getCronJobByCode(cronCode);
            ModelMapper modelMapper = new ModelMapper();
            CronJobModel userDTO = modelMapper.map(cronJob, CronJobModel.class);
            if ("SCHEDULE_01".equalsIgnoreCase(userDTO.getCode()))
                thirdTaskSchedule.startJob(userDTO.getPattern());
            if ("SCHEDULE_02".equalsIgnoreCase(userDTO.getCode()))
                fourthTaskSchedule.startJob(userDTO.getPattern());

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
                    thirdTaskSchedule.startJob(userDTO.getPattern());
                if ("SCHEDULE_02".equalsIgnoreCase(userDTO.getCode()))
                    fourthTaskSchedule.startJob(userDTO.getPattern());
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
            thirdTaskSchedule.stop();
            fourthTaskSchedule.stop();
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
                thirdTaskSchedule.stop();
            if ("SCHEDULE_02".equalsIgnoreCase(cronCode))
                fourthTaskSchedule.stop();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return cronJob;
    }
}
