package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.cliente.Cliente;
import com.tallerwebi.dominio.cliente.ClienteRepositorio;
import com.tallerwebi.dominio.conductor.Conductor;
import com.tallerwebi.dominio.paquete.Paquete;
import com.tallerwebi.dominio.viaje.Viaje;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class ClienteRepositorioImpl implements ClienteRepositorio {

    private SessionFactory sessionFactory;

    public ClienteRepositorioImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    @Transactional
    public void crearViaje(Viaje viaje) {
        this.sessionFactory.getCurrentSession().save(viaje);
    }

    @Override
    @Transactional
    public Viaje buscarViajePorId(Integer id) {
        return this.sessionFactory.getCurrentSession().get(Viaje.class, id);
    }

    @Override
    @Transactional
    public Cliente registrarCliente(Cliente cliente) {
        Session session = this.sessionFactory.getCurrentSession();
        session.save(cliente);
        Integer idConductorGuardado = (Integer) session.getIdentifier(cliente);
        return session.get(Cliente.class, idConductorGuardado);
    }

    @Override
    @Transactional
    public Cliente buscarDuplicados(String email, String nombreUsuario) {
        Session session = this.sessionFactory.getCurrentSession();
        Query query = session.createQuery("FROM Cliente cliente WHERE cliente.email = :email OR cliente.nombreUsuario = :nombreUsuario");
        query.setParameter("email", email);
        query.setParameter("nombreUsuario", nombreUsuario);
        return (Cliente) query.uniqueResult();
    }

    @Override
    public Cliente obtenerClientePorId(Integer idusuario) {
        return this.sessionFactory.getCurrentSession().get(Cliente.class, idusuario);
    }

    @Override
    public void guardarPaquete(Paquete paquete) {
        this.sessionFactory.getCurrentSession().save(paquete);
    }
}
