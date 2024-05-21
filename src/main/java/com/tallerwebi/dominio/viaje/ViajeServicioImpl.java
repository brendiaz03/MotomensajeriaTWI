package com.tallerwebi.dominio.viaje;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ViajeServicioImpl implements ViajeServicio {

    private ViajeRepositorio viajeRepositorio;

    @Autowired
    public ViajeServicioImpl(ViajeRepositorio viajeRepositorio){
        this.viajeRepositorio = viajeRepositorio;
    }

    @Override
    public List<Viaje> obtenerTodosLosViajesDeLaBaseDeDatos() {
        return this.viajeRepositorio.obtenerTodosLosViajesDeLaBaseDeDatos();
    }

    public List<Viaje> obtenerLasSolicitudesDeViajesPendientes() {
        return this.viajeRepositorio.obtenerLasSolicitudesDeViajesPendientes();
    }

    public Viaje actualizarViajeConElIdDelConductorQueAceptoElViaje(Integer idViaje, Integer idConductor) {
        return this.viajeRepositorio.actualizarViajeAceptadoPorElConductor(idViaje, idConductor);
    }

    @Override
    public List<Viaje> obtenerLosViajesAceptadosPorElConductor(Integer idConductor) {
        return this.viajeRepositorio.obtenerLosViajesAceptadosPorElConductor(idConductor);
    }

    @Override
    public Viaje actualizarViajeConElIdDelConductorQueAceptoElViajeYDespuesLoRechaza(Integer idViaje, Integer idConductor) {
        return this.viajeRepositorio.actualizarViajeConElIdDelConductorQueAceptoElViajeYDespuesLoRechaza(idViaje, idConductor);
    }
}