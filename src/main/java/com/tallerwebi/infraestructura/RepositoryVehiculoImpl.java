package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.enums.TipoVehiculo;
import com.tallerwebi.dominio.vehiculo.IRepositoryVehiculo;
import com.tallerwebi.dominio.vehiculo.Vehiculo;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;

public class RepositoryVehiculoImpl implements IRepositoryVehiculo {

    private List<Vehiculo> vehiculos;

    public RepositoryVehiculoImpl() {

        this.vehiculos = new ArrayList<>();
       /* vehiculos.add(new Vehiculo(1L, TipoVehiculo.AUTO));
        vehiculos.add(new Vehiculo(2L, TipoVehiculo.AUTO));
        vehiculos.add(new Vehiculo(3L, TipoVehiculo.MOTO));
*/
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

}