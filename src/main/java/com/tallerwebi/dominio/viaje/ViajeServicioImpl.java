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

        for (Viaje v : viajes) {
            if (!v.getCancelado() && !v.getTerminado()) {
                viajesEnProceso.add(v);
            }
        }
        return viajesEnProceso;
    }

}