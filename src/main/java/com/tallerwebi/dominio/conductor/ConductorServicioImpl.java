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
    public Conductor registrarConductorNoDuplicado(DatosUsuario nuevoConductor) throws UsuarioDuplicadoException {
        Conductor conductorARegistrar = mapearUsuarioAConductor(nuevoConductor);
        try{
            this.conductorRepositorio.buscarDuplicados(nuevoConductor.getEmail(),nuevoConductor.getNombreUsuario());
            throw new UsuarioDuplicadoException("E-mail o Usuario Duplicado");
        }catch(NoResultException e){
           return this.conductorRepositorio.guardar(conductorARegistrar);
        }
}

    private Conductor mapearUsuarioAConductor(DatosUsuario nuevoConductor) {
        return new Conductor(nuevoConductor.getNombre(), nuevoConductor.getApellido(), nuevoConductor.getNumeroDeDni(), nuevoConductor.getEmail(), nuevoConductor.getNumeroDeTelefono(), nuevoConductor.getNombreUsuario(), nuevoConductor.getPassword(), nuevoConductor.getDomicilio(), TipoUsuario.Conductor);
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
    public void editarConductor(Conductor conductorEditado) throws UsuarioNoEncontradoException {
        try{
            Conductor conductor = this.conductorRepositorio.buscarConductorPorId(conductorEditado.getId());
            conductorEditado.setVehiculo(conductor.getVehiculo());
            conductorEditado.setTipoUsuario(conductor.getTipoUsuario());
            if(conductorEditado.getImagenPerfil()==null){
                conductorEditado.setImagenPerfil(conductor.getImagenPerfil());
            }
            this.conductorRepositorio.editarConductor(conductorEditado);
        }catch (NoResultException e){
            throw new UsuarioNoEncontradoException("No se pudo editar al conductor ya que el mismo no existe.");
        }
    }

    @Override
    public void borrarConductor(Integer idusuario) throws UsuarioNoEncontradoException {
        try{
            Conductor conductorABorrar= this.conductorRepositorio.buscarConductorPorId(idusuario);
            this.conductorRepositorio.borrarConductor(conductorABorrar);
        }catch (NoResultException e){
            throw new UsuarioNoEncontradoException("No se pudo borrar al conductor ya que el mismo no existe.");
        }
    }

    @Override
    public void ingresarImagen(MultipartFile imagen, Integer idUsuario) throws IOException, UsuarioNoEncontradoException {
        try{
            Conductor conductor = this.conductorRepositorio.buscarConductorPorId(idUsuario);
            conductor.setImagenPerfil(Base64.getEncoder().encode(imagen.getBytes()));
            this.editarConductor(conductor);

        }catch (NoResultException e){
            throw new UsuarioNoEncontradoException("No se pudo ingresar la imagen ya que el Conductor no existe.");
        } catch (IOException e) {
        throw new RuntimeException("Error al ingresar la imagen", e);
    }
    }

    @Override
    public Boolean RelacionarVehiculoAConductor(Integer idConductor, Vehiculo vehiculo) throws UsuarioNoEncontradoException {
        try{
            conductorRepositorio.agregarVehiculoAConductor(idConductor,vehiculo);
            return true;
        }catch(NoResultException e){
            throw new UsuarioNoEncontradoException("No se pudo realizar la relación entre el Vehiculo y el Conductor ya que el Conductor no existe.");
        }
    }


}
