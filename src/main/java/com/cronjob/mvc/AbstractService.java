package com.cronjob.mvc;

import org.springframework.stereotype.Service;

import java.io.Serializable;

@Service
public abstract class AbstractService< Entity, PrimaryKey extends Serializable> {

    protected AbstractRepository<Entity, PrimaryKey> abstractDao;

    public AbstractService(AbstractRepository<Entity, PrimaryKey> abstractDao) {
        this.abstractDao = abstractDao;
    }
}