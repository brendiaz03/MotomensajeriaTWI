package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.cliente.Cliente;
import com.tallerwebi.dominio.cliente.ClienteRepositorio;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
public class ClienteRepositorioImpl implements ClienteRepositorio {

    private SessionFactory sessionFactory;

    public ClienteRepositorioImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    @Transactional
    public Cliente obtenerClientePorId(Integer id) {
        String hql = "FROM Usuario WHERE id =:id";
        Query query = this.sessionFactory.getCurrentSession().createQuery(hql);
        query.setParameter("id", id);
        return (Cliente) query.getSingleResult();
    }

}
