package com.tallerwebi.dominio.viaje;

import com.tallerwebi.dominio.conductor.Conductor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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
        if(viajes.isEmpty()){
            return null;
        }else{
            return viajes;
        }
    }

    @Override
    public Viaje obtenerViajeAceptadoPorId(Integer id) {
        return viajeRepositorio.obtenerViajePorId(id);
    }

    @Override
    public List<Viaje> obtenerHistorialDeViajes(Conductor conductor) {
        List<Viaje> viajes = viajeRepositorio.obtenerViajesPorConductor(conductor);
        List<Viaje> historial = new ArrayList<>();
        for (Viaje v : viajes) {
            if (v.getCancelado() || v.getTerminado()) {
                historial.add(v);
            }
        }
        return historial;
    }

    @Override
    public Viaje actualizarViaje(Viaje viaje) {
         viajeRepositorio.editar(viaje);
         return viaje;
    }

    @Override
    public List<Viaje> obtenerViajesEnProceso(Conductor conductor) {
        List<Viaje> viajes = viajeRepositorio.obtenerViajesPorConductor(conductor);
        List<Viaje> viajesEnProceso = new ArrayList<>();

        for (Viaje viaje : viajes) {
            if (!viaje.getCancelado() && !viaje.getTerminado()) {
                viajesEnProceso.add(viaje);
            }
        }
        return viajesEnProceso;
    }

    @Override
    public void descartarViaje(Integer idViaje, Conductor conductor) {
        Viaje viaje = this.viajeRepositorio.obtenerViajePorId(idViaje);
        viaje.setDescartado(true);
        viaje.setConductor(conductor);
        this.viajeRepositorio.editar(viaje);
    }

    @Override
    public Boolean estaPenalizado(Conductor conductor) {
        List<Viaje> viajesObtenidos = this.viajeRepositorio.obtenerViajesPorConductor(conductor);
        List<Viaje> viajesDescartados = new ArrayList<>();
        boolean isPenalizado = false;
        for (Viaje viaje : viajesObtenidos) {
            if(viaje.getDescartado()){
                viajesDescartados.add(viaje);
            }
        }

        if(viajesDescartados.size() >= 5){
            isPenalizado = true;
        }

        return isPenalizado;
    }

    @Override
    public List<Viaje> filtrarViajesPorDistanciaDelConductor(Double latitudConductor, Double longitudConductor, Double distanciaAFiltrar) {
        return this.viajeRepositorio.encontrarViajesCercanos(latitudConductor, longitudConductor, distanciaAFiltrar);
    }
}