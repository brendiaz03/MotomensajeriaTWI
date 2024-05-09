package com.tallerwebi.dominio.vehiculo;
import com.tallerwebi.dominio.conductor.Conductor;
import com.tallerwebi.dominio.conductor.IRepositoryConductor;
import com.tallerwebi.dominio.enums.TipoVehiculo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import java.util.ArrayList;
import java.util.List;

@Service
public class ServicioVehiculoImpl implements IServicioVehiculo {

    private final IRepositoryVehiculo iRepositoryVehiculo;

    @Autowired
    public ServicioVehiculoImpl(IRepositoryVehiculo _iRepositoryVehiculo) {
        this.iRepositoryVehiculo = _iRepositoryVehiculo;
    }
    @Override
    public Boolean registrarVehiculoSiPatenteNoEstaYaCargada(Vehiculo vehiculo) {
        System.out.println(vehiculo.getPatente());

        Vehiculo buscado = iRepositoryVehiculo.buscarVehiculoPorPatente(vehiculo.getPatente());
        if (buscado != null) {
            return false;
        } else {
            iRepositoryVehiculo.registrarVehiculo(vehiculo);
            return true;
        }
    }
}



