package com.tallerwebi.dominio.usuario;

import com.tallerwebi.dominio.cliente.Cliente;
import com.tallerwebi.dominio.conductor.Conductor;
import com.tallerwebi.dominio.enums.TipoUsuario;
import com.tallerwebi.dominio.exceptions.UsuarioDuplicadoException;
import com.tallerwebi.dominio.exceptions.UsuarioNoEncontradoException;
import com.tallerwebi.presentacion.Datos.DatosUsuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.NoResultException;
import java.io.IOException;
import java.util.Base64;
import java.util.Optional;

@Service
public class UsuarioServicioImpl implements UsuarioServicio {

    private UsuarioRepositorio usuarioRepositorio;

    @Autowired
    public UsuarioServicioImpl(UsuarioRepositorio usuarioRepositorio) {
        this.usuarioRepositorio = usuarioRepositorio;
    }

    @Override
    public Usuario registrarUsuario(DatosUsuario usuario) throws UsuarioDuplicadoException {
        try{
            verificarDuplicados(usuario.getEmail(), usuario.getNombreUsuario());
            if(usuario.getTipoUsuario() == TipoUsuario.Conductor){
                Conductor conductor = usuario.toConductor();
                return usuarioRepositorio.guardarUsuario(conductor);
            }else{
                Cliente cliente = usuario.toCliente();
                return usuarioRepositorio.guardarUsuario(cliente);
            }
        }catch (Exception e){
            throw new UsuarioDuplicadoException("Usuario duplicado");
        }
    }

    @Override
    public void actualizarUsuario(DatosUsuario usuario, TipoUsuario tipoUsuario) throws UsuarioNoEncontradoException {
        try{
        if(TipoUsuario.Conductor == tipoUsuario) {
            Conductor usuarioEditado = usuario.toConductor();
            Conductor conductor = (Conductor) this.usuarioRepositorio.getUsuarioById(usuarioEditado.getId());
            usuarioEditado.setVehiculo(conductor.getVehiculo());
            usuarioEditado.setTipoUsuario(conductor.getTipoUsuario());
            usuarioEditado.setImagenPerfil(conductor.getImagenPerfil());
            usuarioRepositorio.editarUsuario(usuarioEditado);
        }else{
            Cliente usuarioEditado = usuario.toCliente();
            Cliente cliente = (Cliente) this.usuarioRepositorio.getUsuarioById(usuarioEditado.getId());
            usuarioEditado.setTipoUsuario(cliente.getTipoUsuario());
            usuarioEditado.setImagenPerfil(cliente.getImagenPerfil());
            usuarioRepositorio.editarUsuario(usuarioEditado);
        }}
        catch (Exception e){
            throw new UsuarioNoEncontradoException("No se encontro el usuario u su tipo");
        }
    }

    @Override
    public Usuario obtenerUsuarioPorId(Integer id) {
        return usuarioRepositorio.getUsuarioById(id);
    }

    @Override
    public void ingresarImagen(MultipartFile imagen, Integer idUsuario) throws UsuarioNoEncontradoException {
        try{
            Usuario usuario = this.usuarioRepositorio.getUsuarioById(idUsuario);
            usuario.setImagenPerfil(Base64.getEncoder().encode(imagen.getBytes()));
            usuarioRepositorio.editarUsuario(usuario);
        }catch (NoResultException e) {
            throw new UsuarioNoEncontradoException("No se pudo ingresar la imagen ya que el Conductor no existe.");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    private void verificarDuplicados(String email, String nombreUsuario) throws UsuarioDuplicadoException {
        Usuario duplicado = usuarioRepositorio.buscarDuplicados(email, nombreUsuario);
        if (duplicado != null) {
            throw new UsuarioDuplicadoException("Email o nombre de usuario ya existe");
        }
    }

}
