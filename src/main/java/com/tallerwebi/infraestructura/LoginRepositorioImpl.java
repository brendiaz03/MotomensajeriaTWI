package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.conductor.Conductor;
import com.tallerwebi.dominio.login.LoginRepositorio;
import com.tallerwebi.dominio.usuario.Usuario;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
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

    public Usuario buscarUsuarioPorUsernameYPassword(String username, String password) {
        return (Usuario) sessionFactory.getCurrentSession().createCriteria(Usuario.class)
                .add(Restrictions.eq("nombreUsuario", username))
                .add(Restrictions.eq("password", password))
                .uniqueResult();
    }
}
