package com.tallerwebi.dominio.login;

import com.tallerwebi.dominio.conductor.Conductor;
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
    public Conductor consultarUsuario(String user, String pass) {
        try{
            Conductor conductor= loginRepositorio.buscarConductorPorUsernameYPassword(user, pass);
            return conductor;
        }catch(Exception e){
            throw new RuntimeException(e);
        }
    }
}
