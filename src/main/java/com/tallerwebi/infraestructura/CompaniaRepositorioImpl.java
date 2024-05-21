package com.tallerwebi.infraestructura;
import com.tallerwebi.dominio.compania.IRepositorioCompania;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

@Repository
public class CompaniaRepositorioImpl implements IRepositorioCompania {

    private SessionFactory sessionFactory;

    public CompaniaRepositorioImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

}
