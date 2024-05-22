package com.tallerwebi.dominio.viaje;

import com.tallerwebi.dominio.conductor.Conductor;
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
    public List<Viaje> obtenerLasSolicitudesDeViajesPendientes() {
        List<Viaje> viajes = viajeRepositorio.obtenerLasSolicitudesDeViajesPendientes();
        if(viajes.size() == 0){
            return null;
        }else{
            return viajes;
        }
    }

    @Override
    public Viaje obtenerViajeAceptadoPorId(Integer id) {
        Viaje viaje = viajeRepositorio.obtenerViajePorId(id);
        if(viaje == null){
            return null;
        }else{
            return viaje;
        }
    }

    @Override
    public List<Viaje> obtenerHistorialDeViajes(Conductor conductor) {
        List<Viaje> historial = viajeRepositorio.obtenerViajesPorConductor(conductor);
        //tratar errores despues
        if(historial.size() == 0){
            return null;
        }else{
            return historial;
        }
    }

    @Override
    public Viaje actualizarViajeConConductor(Viaje viaje) {
         viajeRepositorio.actualizarViaje(viaje);
         return viaje;
    }

//    @Override
//    public List<Viaje> obtenerTodosLosViajesDeLaBaseDeDatos() {
//        return this.viajeRepositorio.obtenerTodosLosViajesDeLaBaseDeDatos();
//    }
//
//    public Viaje actualizarViajeConElIdDelConductorQueAceptoElViaje(Integer idViaje, Integer idConductor) {
//        return this.viajeRepositorio.actualizarViajeAceptadoPorElConductor(idViaje, idConductor);
//    }
//
//    @Override
//    public List<Viaje> obtenerLosViajesAceptadosPorElConductor(Integer idConductor) {
//        return this.viajeRepositorio.obtenerLosViajesAceptadosPorElConductor(idConductor);
//    }
//
//    @Override
//    public Viaje actualizarViajeConElIdDelConductorQueAceptoElViajeYDespuesLoRechaza(Integer idViaje, Integer idConductor) {
//        return this.viajeRepositorio.actualizarViajeConElIdDelConductorQueAceptoElViajeYDespuesLoRechaza(idViaje, idConductor);
//    }
}