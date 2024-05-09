package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.enums.TipoVehiculo;
import com.tallerwebi.dominio.vehiculo.IRepositoryVehiculo;
import com.tallerwebi.dominio.vehiculo.Vehiculo;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;

@Repository
public class RepositoryVehiculoImpl implements IRepositoryVehiculo {

    private List<Vehiculo> vehiculos;

    private SessionFactory sessionFactory;

    @Autowired
    public RepositoryVehiculoImpl(SessionFactory sessionFactory) {

        this.vehiculos = new ArrayList<>();
        vehiculos.add(new Vehiculo(1L, TipoVehiculo.AUTO));
        vehiculos.add(new Vehiculo(2L, TipoVehiculo.AUTO));
        vehiculos.add(new Vehiculo(3L, TipoVehiculo.MOTO));

        this.sessionFactory = sessionFactory;
    }

    @Override
    public List<Vehiculo> get() {
        return this.vehiculos;
    }

    @Override
    public List<Vehiculo> getByTipoVehiculo(TipoVehiculo tipoVehiculo) {

        List<Vehiculo> vehiculosFiltrados = new ArrayList<>();

        for (Vehiculo vehiculo : this.vehiculos) {

            if (vehiculo.getTipoDeVehiculo().equals(tipoVehiculo)) {
                vehiculosFiltrados.add(vehiculo);
            }

        }
        return vehiculosFiltrados;
    }

    @Override
    public List<Vehiculo> obtenerTodosLosVehiculos() {
        return this.sessionFactory.getCurrentSession().createQuery("FROM Vehiculo", Vehiculo.class).list();
    }

    @Override
    public Vehiculo obtenerVehiculoPorElIdDelConductor(Integer id) {
        Session session = this.sessionFactory.getCurrentSession();
        Query query = session.createQuery("FROM Vehiculo WHERE idConductor = :id");
        query.setParameter("id", id);
        return (Vehiculo) query.uniqueResult();
    }

    @Override
    public List<Vehiculo> obtenerTodosLosVehiculosDelConductor(Integer id) {
        Session session = this.sessionFactory.getCurrentSession();
        return session.createQuery("FROM Vehiculo WHERE idConductorTest = 1").list();
        /*Query query = session.createQuery("FROM Vehiculo WHERE idConductorTest = :id");
        query.setParameter("id", id);
        return (List<Vehiculo>) query.list();*/
    }

}