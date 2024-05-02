package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.LoginRepository;
import com.tallerwebi.dominio.entidades.usuarios.Cliente;
import com.tallerwebi.dominio.entidades.usuarios.Conductor;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class LoginRepositoryImpl implements LoginRepository {

    private SessionFactory sessionFactory;

    @Autowired
    public LoginRepositoryImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public void guardarConductor(Conductor conductor) {
        sessionFactory.getCurrentSession().save(conductor);
    }

    @Override
    public void guardarCliente(Cliente cliente) {
        sessionFactory.getCurrentSession().save(cliente);
    }

    @Override
    public Cliente validarCliente(String email, String password) {
        return (Cliente) sessionFactory.getCurrentSession().createCriteria(Cliente.class)
                .add(Restrictions.eq("email", email))
                .add(Restrictions.eq("password", password))
                .uniqueResult();

        //SELECT * FROM Cliente WHERE email = email AND password = password;
    }

    @Override
    public Conductor validarConductor(String email, String password) {
        return (Conductor) sessionFactory.getCurrentSession().createCriteria(Conductor.class)
                .add(Restrictions.eq("email", email))
                .add(Restrictions.eq("password", password))
                .uniqueResult();
    }
}