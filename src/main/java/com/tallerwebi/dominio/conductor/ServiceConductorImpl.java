package com.tallerwebi.dominio.conductor;

import com.tallerwebi.infraestructura.RepositoryConductorImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.regex.Pattern;
import java.util.ArrayList;
import java.util.List;

@Service
public class ServiceConductorImpl implements IServiceConductor {

    private final IRepositoryConductor iRepositoryConductor;

    @Autowired
    public ServiceConductorImpl(IRepositoryConductor iRepositoryConductor) {
        this.iRepositoryConductor = iRepositoryConductor;
    }

//    @Override
//    public List<Conductor> get() {
//        return null;
//    }

//    @Override
//    public List<Conductor> obtenerConductoresPorDomicilio(String domicilio) { //Prueba
//        List<Conductor> conductoresFitlrados = new ArrayList<>();
//
//        for (Conductor conductor: this.conductores){
//            if (conductor.getDomicilio().equals(domicilio)){
//                conductoresFitlrados.add(conductor);
//            }
//        }
//        return conductoresFitlrados;
//   }

    @Override
    public Boolean verificarDatosDeRegistro(Conductor nuevoConductor) throws DniInvalidoException,EmailInvalidoException,PasswordInvalidoException,CVUInvalidoException {
        if (this.verificarDniValido(nuevoConductor.getNumeroDeDni()) && this.verificarEmailValido(nuevoConductor.getEmail())
                && this.verificarPasswordValido(nuevoConductor.getPassword()) && this.verificarCVUValido(nuevoConductor.getCvu())) {
            if (this.iRepositoryConductor.registrar(nuevoConductor)) {
                return true;
            }
        }
       return false;
    }

    public Boolean verificarDniValido(Integer dni) throws DniInvalidoException {
        if (dni>= 10000000 && dni <= 99999999) {
            return true;
        } else {
            throw new DniInvalidoException("DNI Inválido");
        }
    }

    public Boolean verificarEmailValido(String email) throws EmailInvalidoException {

        String filtroEmail = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        Pattern filtroEmailP = Pattern.compile(filtroEmail);
        if (filtroEmailP.matcher(email).matches()) {
            return true;
        } else {
            throw new EmailInvalidoException("E-mail Inválido");
        }
    }
    public Boolean verificarPasswordValido(String password) throws PasswordInvalidoException {
        if (password.length() >= 6 && password.length() <= 20) {
            return true;
        } else {
        throw new PasswordInvalidoException("Contraseña Inválida");
        }
    }
    public Boolean verificarCVUValido(String cvu) throws CVUInvalidoException {
        if (cvu.length() == 22) {
        return true;
        }
        throw new CVUInvalidoException("CVU Inválido");
        }

}
