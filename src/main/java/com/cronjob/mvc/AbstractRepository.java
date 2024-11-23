package com.cronjob.mvc;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;



import java.io.Serializable;

@Repository
public abstract class AbstractRepository <Entity, PrimaryKey extends Serializable> {

    private Class<Entity> entityClass;

    @Autowired
    protected SessionFactory sessionFactory;

    public AbstractRepository(Class<Entity> clazz) {
        this.entityClass = clazz;
    }

    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public Entity findOne(PrimaryKey id) {
        return getCurrentSession().get(entityClass, id);
    }

    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public Entity find(PrimaryKey id) {
        return getCurrentSession().get(entityClass, id);
    }

    protected final Session getCurrentSession() { ;
        return sessionFactory.getCurrentSession();
    }
}