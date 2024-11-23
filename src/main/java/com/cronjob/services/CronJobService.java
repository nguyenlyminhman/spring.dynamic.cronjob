package com.cronjob.services;

import com.cronjob.entities.CronJobEntity;

import com.cronjob.mvc.AbstractService;
import com.cronjob.repositories.CronJobRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CronJobService extends AbstractService<CronJobEntity, Long> {

    @Autowired
    private final CronJobRepository cronJobRepository;

    public CronJobService( CronJobRepository cronJobRepository) {
        super(cronJobRepository);
        this.cronJobRepository = cronJobRepository;
    }

    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public List<CronJobEntity> findAll() {
        return cronJobRepository.getAllByQuery();
    }

    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public CronJobEntity findByCode(String cronCode) {
        return cronJobRepository.getCronJobByCode(cronCode);
    }
}
