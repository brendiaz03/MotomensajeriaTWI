package com.tallerwebi.dominio.login;

import com.tallerwebi.dominio.conductor.Conductor;
import com.tallerwebi.dominio.conductor.IRepositoryConductor;
import com.tallerwebi.dominio.imagen.IImagenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.security.MessageDigest;

import javax.transaction.Transactional;

@Service("servicioLogin")
@Transactional
public class LoginService implements ILoginService {
    private final ILoginRepository iLoginRepository;

    @Autowired
    public LoginService(ILoginRepository _iLoginRepository) {
        this.iLoginRepository = _iLoginRepository;
    }

    @Override
    public Conductor consultarUsuario(String user, String pass) {
        try{
            Conductor conductor= iLoginRepository.buscarConductorPorUsernameYPassword(user, pass);
            return conductor;
        }catch(Exception e){
            throw new RuntimeException(e);
        }
    }
}
