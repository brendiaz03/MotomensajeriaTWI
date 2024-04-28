package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.LoginRepository;
import com.tallerwebi.dominio.LoginService;
import com.tallerwebi.dominio.entidades.usuarios.Cliente;
import com.tallerwebi.dominio.entidades.usuarios.Conductor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class LoginServiceImpl implements LoginService {

    private LoginRepository loginRepository;

    @Autowired
    public LoginServiceImpl(LoginRepository loginRepository) {
        this.loginRepository = loginRepository;
    }

    @Override
    public void guardarConductor(Conductor conductor) {
        this.loginRepository.guardarConductor(conductor);
    }

    @Override
    public void guardarCliente(Cliente cliente) {
        this.loginRepository.guardarCliente(cliente);
    }

    @Override
    public Conductor consultarConductor(String email, String password) {
        return this.loginRepository.buscarConductor(email, password);
    }

    @Override
    public Cliente consultarCliente(String email, String password) {
        return this.loginRepository.buscarCliente(email, password);
    }
}
