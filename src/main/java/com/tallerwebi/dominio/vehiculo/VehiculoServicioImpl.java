package com.tallerwebi.dominio.vehiculo;
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
    public Boolean registrarVehiculoSiPatenteNoEstaYaCargada(Vehiculo vehiculo) {
        System.out.println(vehiculo.getPatente());

        Vehiculo buscado = vehiculoRepositorio.buscarVehiculoPorPatente(vehiculo.getPatente());
        if (buscado != null) {
            return false;
        } else {
            vehiculoRepositorio.registrarVehiculo(vehiculo);
            return true;
        }
    }
}



