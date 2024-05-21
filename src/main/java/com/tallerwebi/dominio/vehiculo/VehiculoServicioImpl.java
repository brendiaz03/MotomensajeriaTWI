package com.tallerwebi.dominio.vehiculo;
import com.tallerwebi.dominio.conductor.Conductor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VehiculoServicioImpl implements VehiculoServicio {

    private final VehiculoRepositorio vehiculoRepositorio;

    @Autowired
    public VehiculoServicioImpl(VehiculoRepositorio _VehiculoRepositorio) {
        this.vehiculoRepositorio = _VehiculoRepositorio;
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

}



