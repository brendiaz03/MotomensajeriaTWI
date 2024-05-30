package com.tallerwebi.dominio.conductor;

import com.tallerwebi.dominio.vehiculo.Vehiculo;
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
    public Conductor registrarConductorNoDuplicado(Conductor nuevoConductor) throws  ConductorDuplicadoException {
        try{
            this.conductorRepositorio.buscarDuplicados(nuevoConductor.getEmail(),nuevoConductor.getNombreUsuario());
            throw new ConductorDuplicadoException("E-mail o Usuario Duplicado");
        }catch(NoResultException e){
           return this.conductorRepositorio.guardar(nuevoConductor);
        }
}

    @Override
    public Conductor obtenerConductorPorId(Integer id) throws ConductorNoEncontradoException {
        try{
            return this.conductorRepositorio.buscarConductorPorId(id);
        }catch(NoResultException e){
            throw new ConductorNoEncontradoException("Usuario no Encontrado");
        }
    }

    @Override
    public void editarConductor(Conductor conductorEditado) throws ConductorNoEncontradoException {
        Conductor conductor = this.conductorRepositorio.buscarConductorPorId(conductorEditado.getId());
        conductorEditado.setVehiculo(conductor.getVehiculo());
        if(conductorEditado.getImagenPerfil()==null){
            conductorEditado.setImagenPerfil(conductor.getImagenPerfil());
        }
        this.conductorRepositorio.editarConductor(conductorEditado);
    }

    @Override
    public void borrarConductor(Integer idusuario) {
        Conductor conductorABorrar= this.conductorRepositorio.buscarConductorPorId(idusuario);
        this.conductorRepositorio.borrarConductor(conductorABorrar);
    }

    @Override
    public void ingresarImagen(MultipartFile imagen, Integer idUsuario) throws IOException, ConductorNoEncontradoException {

        Conductor conductor = this.conductorRepositorio.buscarConductorPorId(idUsuario);
        if (conductor!=null){
            conductor.setImagenPerfil(Base64.getEncoder().encode(imagen.getBytes()));
            this.editarConductor(conductor);
        }else{
            System.out.println("Error");
        }
    }

    @Override
    public Boolean RelacionarVehiculoAConductor(Integer idConductor, Vehiculo vehiculo) {
        try{
            conductorRepositorio.agregarVehiculoAConductor(idConductor,vehiculo);
            return true;
        }catch(NoResultException e){
            return false;
        }
    }


}
