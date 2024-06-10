package com.tallerwebi.dominio.conductor;

import com.tallerwebi.dominio.enums.TipoUsuario;
import com.tallerwebi.dominio.exceptions.UsuarioDuplicadoException;
import com.tallerwebi.dominio.exceptions.UsuarioNoEncontradoException;
import com.tallerwebi.dominio.usuario.Usuario;
import com.tallerwebi.dominio.vehiculo.Vehiculo;
import com.tallerwebi.presentacion.Datos.DatosUsuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.NoResultException;
import java.io.IOException;
import java.util.Base64;

@Service
public class ConductorServicioImpl implements ConductorServicio {

    private final ConductorRepositorio conductorRepositorio;

    @Autowired
    public ConductorServicioImpl(ConductorRepositorio conductorRepositorio) {
        this.conductorRepositorio = conductorRepositorio;
    }


    @Override
    public Conductor obtenerConductorPorId(Integer id) throws UsuarioNoEncontradoException {
        try{
            return this.conductorRepositorio.buscarConductorPorId(id);
        }catch(NoResultException e){
            throw new UsuarioNoEncontradoException("Conductor no Encontrado");
        }
    }

    @Override
    public Boolean RelacionarVehiculoAConductor(Integer idConductor, Vehiculo vehiculo) throws UsuarioNoEncontradoException {
        try{
            Conductor conductor = this.obtenerConductorPorId(idConductor);
            conductor.setVehiculo(vehiculo);
            conductorRepositorio.editarConductor(conductor);
            return true;
        }catch(NoResultException e){
            throw new UsuarioNoEncontradoException("No se pudo realizar la relación entre el Vehiculo y el Conductor ya que el Conductor no existe.");
        }
    }


}
