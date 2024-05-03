package com.tallerwebi.dominio.conductor;

import com.tallerwebi.infraestructura.RepositorioConductorImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.*;

import javax.transaction.Transactional;

@Service("servicioConductor")
@Transactional
public class ServiceConductorImpl implements IServiceConductor {
    //implementa su propia interfaz con metodos que luego se pueden usar en el controller
    //se le inyecta el repositorio para poder usar los metodos que conectan con la base de datos
    //es un intermedio entre los controllers y los repositorys

    private RepositorioConductorImpl repositorioConductor;

    @Autowired
    public ServiceConductorImpl(RepositorioConductorImpl _repositorioConductor){
        this.repositorioConductor = _repositorioConductor;
    }


    @Override
    public void verificarIngresoConductor(Conductor conductor) {
        Conductor nuevo = repositorioConductor.buscarConductorPorNroDni(conductor.getNumeroDeDni());
        if(nuevo == null){
            repositorioConductor.ingresarConductor(conductor);
        }else {
            System.out.println("habria que tirar una exception y hacerlo con un trycatch en general");
        }
    }
}
