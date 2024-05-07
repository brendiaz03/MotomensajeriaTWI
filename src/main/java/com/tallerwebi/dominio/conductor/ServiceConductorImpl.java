package com.tallerwebi.dominio.conductor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.regex.Pattern;

@Service
public class ServiceConductorImpl implements IServiceConductor {

    private IRepositoryConductor iRepositoryConductor;

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
    public String verificarDatosDeRegistro(Conductor nuevoConductor) throws Exception {

        String filtroEmail = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        Pattern filtroEmailP = Pattern.compile(filtroEmail);

        Boolean dniCorrecto = false;
        Boolean emailCorrecto = false;
        Boolean passwordCorrecto = false;
        Boolean cvuCorrecto = false;

        if (nuevoConductor.getNumeroDeDni() >= 10000000 && nuevoConductor.getNumeroDeDni() <= 99999999) {
            dniCorrecto = true;
            if (filtroEmailP.matcher(nuevoConductor.getEmail()).matches()) {
                emailCorrecto = true;
                if (nuevoConductor.getPassword().length() >= 6 && nuevoConductor.getPassword().length() <= 20) {
                    passwordCorrecto = true;
                    if (nuevoConductor.getCvu().length() == 22) {
                        cvuCorrecto = true;
                    } else {
                        throw new Exception("cvuInvalido");
                    }
                } else {
                    throw new Exception("passwordInvalido");
                }
            } else {
                throw new Exception("emailInvalido");
            }
        } else {
            throw new Exception("dniInvalido");
        }

        if (dniCorrecto && emailCorrecto && passwordCorrecto && cvuCorrecto) {
            System.out.println(nuevoConductor);

            this.iRepositoryConductor.registrar(nuevoConductor);
            return "Datos cargados con exito";
        } else {
            return "Error";
        }

        }


    }
