package com.tallerwebi.dominio.vehiculo;
import com.tallerwebi.dominio.conductor.Conductor;
import com.tallerwebi.dominio.exceptions.UsuarioDuplicadoException;
import com.tallerwebi.dominio.exceptions.VehiculoDuplicadoException;
import com.tallerwebi.dominio.usuario.Usuario;
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
    public Vehiculo registrarVehiculo(Vehiculo vehiculo) throws VehiculoDuplicadoException {
        try{
            verificarDuplicados(vehiculo.getPatente());
            return vehiculoRepositorio.guardarVehiculo(vehiculo);
        }catch(VehiculoDuplicadoException e){
            throw new VehiculoDuplicadoException("Patente duplicada");
        }
    }

    @Override
    public void actualizarVehiculo(Vehiculo vehiculo) {
        vehiculoRepositorio.editar(vehiculo);
    }

    private void verificarDuplicados(String patente) throws VehiculoDuplicadoException {
        Vehiculo duplicado = vehiculoRepositorio.buscarVehiculoPorPatente(patente);
        if (duplicado != null) {
            throw new VehiculoDuplicadoException("La patente del vehiculo ya existe");
        }
    }
}



