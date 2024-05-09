package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.enums.TipoVehiculo;
import com.tallerwebi.dominio.vehiculo.IRepositoryVehiculo;
import com.tallerwebi.dominio.vehiculo.Vehiculo;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Repository
public class RepositoryVehiculoImpl implements IRepositoryVehiculo {
    private SessionFactory sessionFactory;
    public RepositoryVehiculoImpl(SessionFactory sessionFactory) {

        this.sessionFactory=sessionFactory;
    }


    @Override
    @Transactional
    public void registrarVehiculo(Vehiculo vehiculo) {
        sessionFactory.getCurrentSession().save(vehiculo);
    }

    @Override
    @Transactional
    public Vehiculo buscarVehiculoPorPatente(String patente) {
        return (Vehiculo) sessionFactory.getCurrentSession().createCriteria(Vehiculo.class)
                .add(Restrictions.eq("patente", patente)).uniqueResult();
    }
}