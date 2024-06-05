package com.tallerwebi.dominio.login;

import com.tallerwebi.dominio.conductor.Conductor;
import com.tallerwebi.dominio.usuario.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service("servicioLogin")
@Transactional
public class LoginServicioImpl implements LoginServicio {
    private final LoginRepositorio loginRepositorio;

    @Autowired
    public LoginServicioImpl(LoginRepositorio _LoginRepositorio) {
        this.loginRepositorio = _LoginRepositorio;
    }

    @Override
    public Usuario consultarUsuario(String user, String pass) {
        try{
            Usuario usuario= loginRepositorio.buscarUsuarioPorUsernameYPassword(user, pass);
            return usuario;
        }catch(Exception e){
            throw new RuntimeException(e);
        }
    }
}
