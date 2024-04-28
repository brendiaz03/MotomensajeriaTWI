package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.PaqueteRepository;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class PaqueteRepositoryImpl implements PaqueteRepository {

    private SessionFactory sessionFactory;

    @Autowired
    public PaqueteRepositoryImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }
}
