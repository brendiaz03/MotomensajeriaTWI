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
    public Conductor registrarConductorNoDuplicado(Conductor nuevoConductor) throws ConductorDuplicadoException {
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
            throw new ConductorNoEncontradoException("Conductor no Encontrado");
        }
    }

    @Override
    public void editarConductor(Conductor conductorEditado) throws ConductorNoEncontradoException {
        try{
            Conductor conductor = this.conductorRepositorio.buscarConductorPorId(conductorEditado.getId());
            conductorEditado.setVehiculo(conductor.getVehiculo());
            if(conductorEditado.getImagenPerfil()==null){
                conductorEditado.setImagenPerfil(conductor.getImagenPerfil());
            }
            this.conductorRepositorio.editarConductor(conductorEditado);
        }catch (NoResultException e){
            throw new ConductorNoEncontradoException("No se pudo editar al conductor ya que el mismo no existe.");
        }
    }

    @Override
    public void borrarConductor(Integer idusuario) throws ConductorNoEncontradoException {
        try{
            Conductor conductorABorrar= this.conductorRepositorio.buscarConductorPorId(idusuario);
            this.conductorRepositorio.borrarConductor(conductorABorrar);
        }catch (NoResultException e){
            throw new ConductorNoEncontradoException("No se pudo borrar al conductor ya que el mismo no existe.");
        }
    }

    @Override
    public void ingresarImagen(MultipartFile imagen, Integer idUsuario) throws IOException, ConductorNoEncontradoException {
        try{
            Conductor conductor = this.conductorRepositorio.buscarConductorPorId(idUsuario);
            conductor.setImagenPerfil(Base64.getEncoder().encode(imagen.getBytes()));
            this.editarConductor(conductor);

        }catch (NoResultException e){
            throw new ConductorNoEncontradoException("No se pudo ingresar la imagen ya que el Conductor no existe.");
        } catch (IOException e) {
        throw new RuntimeException("Error al ingresar la imagen", e);
    }
    }

    @Override
    public Boolean RelacionarVehiculoAConductor(Integer idConductor, Vehiculo vehiculo) throws ConductorNoEncontradoException {
        try{
            conductorRepositorio.agregarVehiculoAConductor(idConductor,vehiculo);
            return true;
        }catch(NoResultException e){
            throw new ConductorNoEncontradoException("No se pudo realizar la relación entre el Vehiculo y el Conductor ya que el Conductor no existe.");
        }
    }


}
