package com.cronjob.repositories;

import com.cronjob.entities.CronJobEntity;
import com.cronjob.models.CronJobModel;
import com.cronjob.mvc.AbstractRepository;
import jakarta.persistence.criteria.*;
import org.springframework.stereotype.Repository;
import org.hibernate.query.Query;


import java.util.List;

@Repository
public class CronJobRepository extends AbstractRepository<CronJobEntity, Long> {
    public CronJobRepository() {
        super(CronJobEntity.class);
    }

    public List<CronJobEntity> getAllByQuery() {
        System.out.println(CronJobEntity.class.getName());
        Query<CronJobEntity> q = getCurrentSession()
                .createQuery("from " + CronJobEntity.class.getName() , CronJobEntity.class );
        return q.getResultList();
    }

    public CronJobEntity getCronJobByCode(String cronCode) {
        CriteriaBuilder builder = getCurrentSession().getCriteriaBuilder();
        CriteriaQuery<CronJobEntity> criteriaQuery = builder.createQuery(CronJobEntity.class);
        Root<CronJobEntity> rootQuery =  criteriaQuery.from(CronJobEntity.class);

        Predicate departmentPredicate = builder.equal(rootQuery.get("code"), cronCode);
        criteriaQuery.where(departmentPredicate);

        return getCurrentSession().createQuery(criteriaQuery).getSingleResult();
    }

}
