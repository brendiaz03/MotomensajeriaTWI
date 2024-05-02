package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.ClienteRepository;
import com.tallerwebi.dominio.Paquete;
import com.tallerwebi.dominio.entidades.usuarios.Cliente;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

@Repository
public class ClienteRepositoryImpl implements ClienteRepository {

    private SessionFactory sessionFactory;

    public ClienteRepositoryImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public Paquete buscarPaquete(Integer idPaquete) {
        final Session session = sessionFactory.getCurrentSession();
        return (Paquete) session.createCriteria(Paquete.class).add(Restrictions.eq("idPaquete", idPaquete)).uniqueResult();
    }

    @Override
    public void guardarPaquete(Paquete paqueteActual) {
        this.sessionFactory.getCurrentSession().save(paqueteActual);
    }
}
