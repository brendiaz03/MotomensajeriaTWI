package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.cliente.Cliente;
import com.tallerwebi.dominio.conductor.Conductor;
import com.tallerwebi.dominio.usuario.Usuario;
import com.tallerwebi.dominio.usuario.UsuarioRepositorio;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class UsuarioRepositorioImpl implements UsuarioRepositorio {

    private SessionFactory sessionFactory;

    public UsuarioRepositorioImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    @Transactional
    public Usuario buscarDuplicados(String email, String nombreUsuario) {
        String hql = "FROM Usuario WHERE email = :email OR nombreUsuario = :nombreUsuario";
        Query query = this.sessionFactory.getCurrentSession().createQuery(hql);
        query.setParameter("email", email);
        query.setParameter("nombreUsuario", nombreUsuario);

        return (Usuario) query.getSingleResult();
    }


    @Override
    @Transactional
    public Usuario guardarUsuario(Usuario nuevoUsuario) {
        Session session = this.sessionFactory.getCurrentSession();
        session.save(nuevoUsuario);
        Integer idClienteGuardado = (Integer) session.getIdentifier(nuevoUsuario);
        return session.get(Usuario.class, idClienteGuardado);
    }

    @Override
    @Transactional
    public void editarUsuario(Usuario usuario) {
            Session session = this.sessionFactory.getCurrentSession();
            session.update(usuario);
    }

    @Override
    @Transactional
    public Usuario getUsuarioById(Integer id) {

        String hql = "FROM Usuario WHERE id =:id";
        Query query = this.sessionFactory.getCurrentSession().createQuery(hql);
        query.setParameter("id", id);
        return (Usuario) query.getSingleResult();
    }

}
