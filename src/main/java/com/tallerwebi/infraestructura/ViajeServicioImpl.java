package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.ViajeRepository;
import com.tallerwebi.dominio.ViajeService;
import com.tallerwebi.dominio.entidades.usuarios.Conductor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ViajeServicioImpl implements ViajeService {

    private ViajeRepository viajeRepository;

    @Autowired
    public ViajeServicioImpl (ViajeRepository viajeRepository){
        this.viajeRepository=viajeRepository;
    }

    @Override
    public void calcularDistancia(Integer posicionInicial, Integer posicionFinal) {
        Integer distanciaCalculada=0;
        if ((posicionFinal-posicionInicial)<0){
            distanciaCalculada=posicionFinal-posicionInicial;

        }
    }

    @Override
    public Conductor buscarConductor(Integer idConductor) {
        if (idConductor != null) {
            return this.viajeRepository.buscarConductor(idConductor);
        } else{
            return null;
        }
    }

}
