package com.tallerwebi.dominio.conductor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.NoResultException;
import java.io.IOException;
import java.util.Base64;

@Service
public class ConductorServicioServicioImpl implements ConductorServicio {

    private final ConductorRepositorio conductorRepositorio;

    @Autowired
    public ConductorServicioServicioImpl(ConductorRepositorio conductorRepositorio) {
        this.conductorRepositorio = conductorRepositorio;
    }

    @Override
    public Boolean verificarDatosDeRegistro(Conductor nuevoConductor) throws  ConductorDuplicadoException {
        try{
            this.conductorRepositorio.buscarDuplicados(nuevoConductor.getEmail(),nuevoConductor.getNombreUsuario());
            throw new ConductorDuplicadoException("E-mail o Usuario Duplicado");
        }catch(NoResultException e){
            this.conductorRepositorio.registrar(nuevoConductor);
            return true;
        }
}

    @Override
    public Conductor obtenerConductorPorId(Integer id) throws ConductorNoEncontradoException {
        try{
            return this.conductorRepositorio.buscarConductor(id);
        }catch(NoResultException e){
            throw new ConductorNoEncontradoException("Usuario no Encontrado");
        }
    }

    @Override
    public void editarConductor(Conductor conductorEditado) throws ConductorNoEncontradoException {
        Conductor conductorEnBD=this.obtenerConductorPorId(conductorEditado.getId());

         if(conductorEditado.getImagenPerfil()==null && (conductorEnBD.getImagenPerfil())==null){
             this.conductorRepositorio.editarConductor(conductorEditado);
         }else if(conductorEditado.getImagenPerfil()==null && conductorEnBD.getImagenPerfil()!=null){
             conductorEditado.setImagenPerfil(conductorEnBD.getImagenPerfil());
             this.conductorRepositorio.editarConductor(conductorEditado);
         }else{
             this.conductorRepositorio.editarConductor(conductorEditado);
         };
    }

    @Override
    public void borrarConductor(Integer idusuario) {
        Conductor conductorABorrar= this.conductorRepositorio.buscarConductor(idusuario);
        this.conductorRepositorio.borrarConductor(conductorABorrar);
    }

    @Override
    public void ingresarImagen(MultipartFile imagen, Integer idUsuario) throws IOException, ConductorNoEncontradoException {

        Conductor conductor = this.conductorRepositorio.buscarConductor(idUsuario);
        if (conductor!=null){
            conductor.setImagenPerfil(Base64.getEncoder().encode(imagen.getBytes()));
            this.editarConductor(conductor);
        }else{
            System.out.println("Error");
        }
    }

}
