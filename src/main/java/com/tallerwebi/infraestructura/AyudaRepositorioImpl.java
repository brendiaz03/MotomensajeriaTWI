package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.ayuda.IRepositorioAyuda;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class AyudaRepositorioImpl implements IRepositorioAyuda {

    private SessionFactory sessionFactory;

    @Autowired
    public AyudaRepositorioImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }
}
