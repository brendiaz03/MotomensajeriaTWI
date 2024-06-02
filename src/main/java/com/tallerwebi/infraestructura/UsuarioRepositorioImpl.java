package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.cliente.Cliente;
import com.tallerwebi.dominio.conductor.Conductor;
import com.tallerwebi.dominio.usuario.UsuarioRepositorio;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.Query;

@Repository
public class UsuarioRepositorioImpl implements UsuarioRepositorio {

    private SessionFactory sessionFactory;

    public UsuarioRepositorioImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    @Transactional
    public Conductor buscarDuplicados(String email, String nombreUsuario) {
        String hql = "FROM Usuario WHERE email=: email OR nombreUsuario=: nombreUsuario";
        Query query = this.sessionFactory.getCurrentSession().createQuery(hql);
        query.setParameter("email", email);
        query.setParameter("nombreUsuario", nombreUsuario);
        return (Conductor) query.getSingleResult();
    }

    @Override
    @Transactional
    public Conductor registrarConductor(Conductor conductorARegistrar) {
        Session session = this.sessionFactory.getCurrentSession();
        session.save(conductorARegistrar);
        Integer idConductorGuardado = (Integer) session.getIdentifier(conductorARegistrar);
        return session.get(Conductor.class, idConductorGuardado);
    }

    @Override
    @Transactional
    public Cliente registrarCliente(Cliente clienteARegistrar) {
        Session session = this.sessionFactory.getCurrentSession();
        session.save(clienteARegistrar);
        Integer idClienteGuardado = (Integer) session.getIdentifier(clienteARegistrar);
        return session.get(Cliente.class, idClienteGuardado);
    }
}
