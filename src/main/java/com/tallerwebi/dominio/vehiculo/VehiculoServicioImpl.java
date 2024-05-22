package com.tallerwebi.dominio.vehiculo;
import com.tallerwebi.dominio.conductor.ConductorRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VehiculoServicioImpl implements VehiculoServicio {

    private final VehiculoRepositorio vehiculoRepositorio;

    private ConductorRepositorio conductorRepositorio;

    @Autowired
    public VehiculoServicioImpl(VehiculoRepositorio _VehiculoRepositorio, ConductorRepositorio conductorRepositorio) {
        this.vehiculoRepositorio = _VehiculoRepositorio;
        this.conductorRepositorio = conductorRepositorio;
    }

    @Override
    public Vehiculo registrarVehiculo(Vehiculo vehiculo) {
        Vehiculo buscado = vehiculoRepositorio.buscarVehiculoPorPatente(vehiculo.getPatente());
        if (buscado == null) {
            return vehiculoRepositorio.guardarVehiculo(vehiculo);
        } else {
            return null;
        }
    }

    @Override
    public void actualizarVehiculo(Vehiculo vehiculo) {
        vehiculoRepositorio.editar(vehiculo);
    }

    @Override
    public Vehiculo buscarVehiculoPorPatente(String patente) {
        return this.vehiculoRepositorio.buscarVehiculoPorPatente(patente);
    }

}



