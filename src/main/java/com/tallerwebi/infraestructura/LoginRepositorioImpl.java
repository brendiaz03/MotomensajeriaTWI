package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.conductor.Conductor;
import com.tallerwebi.dominio.login.LoginRepositorio;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import javax.persistence.NoResultException;
import javax.persistence.Query;

@Repository
public class LoginRepositorioImpl implements LoginRepositorio {
    private SessionFactory sessionFactory;
    public LoginRepositorioImpl(SessionFactory sessionFactory) {
        this.sessionFactory=sessionFactory;
    }

    @Override
    public Conductor buscarConductorPorUsernameYPassword(String username, String password) {
        String hql= "FROM Conductor WHERE nombreUsuario = :nombreUsuario AND password = :password";
        Query query = this.sessionFactory.getCurrentSession().createQuery(hql);
        query.setParameter("nombreUsuario", username);
        query.setParameter("password", password);
        try {
            return (Conductor) query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }

    }
}
