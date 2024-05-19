package com.tallerwebi.dominio.viaje;

import com.tallerwebi.dominio.Datos.DatosViaje;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ViajeServiceImpl implements ViajeService {

    private ViajeRepository viajeRepository;

    @Autowired
    public ViajeServiceImpl(ViajeRepository viajeRepository){
        this.viajeRepository = viajeRepository;
    }

    @Override
    public List<Viaje> obtenerTodosLosViajesDeLaBaseDeDatos() {
        return this.viajeRepository.obtenerTodosLosViajesDeLaBaseDeDatos();
    }

    public List<Viaje> obtenerLasSolicitudesDeViajesPendientes() {
        return this.viajeRepository.obtenerLasSolicitudesDeViajesPendientes();
    }

    public Viaje actualizarViajeConElIdDelConductorQueAceptoElViaje(Integer idViaje, Integer idConductor) {
        return this.viajeRepository.actualizarViajeAceptadoPorElConductor(idViaje, idConductor);
    }

    @Override
    public List<Viaje> obtenerLosViajesAceptadosPorElConductor(Integer idConductor) {
        return this.viajeRepository.obtenerLosViajesAceptadosPorElConductor(idConductor);
    }
}