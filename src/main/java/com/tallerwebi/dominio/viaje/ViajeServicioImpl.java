package com.tallerwebi.dominio.viaje;

import com.tallerwebi.dominio.conductor.Conductor;
import com.tallerwebi.presentacion.Datos.DatosViaje;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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
    public List<DatosViaje> filtrarViajesPorDistanciaDelConductor(Double latitudConductor, Double longitudConductor, Double distanciaAFiltrar) {
        List<Viaje> viajesCercanos = this.viajeRepositorio.encontrarViajesCercanos(latitudConductor, longitudConductor, distanciaAFiltrar);
        return viajesCercanos.stream().limit(5)
                .map(viaje -> new DatosViaje(viaje.getId(), viaje.getDomicilioDeSalida(), viaje.getDomicilioDeLlegada(), viaje.getCliente().getNombre(), viaje.getPrecio(), viaje.getCodigoPostal(), viaje.getLatitudDeSalida(), viaje.getLongitudDeSalida(), viaje.getLatitudDeLlegada(), viaje.getLongitudDeLlegada()))
                .collect(Collectors.toList());
    }

    @Override
    public void asginarConductorAlViaje(Viaje viaje, Conductor conductor) {
        viaje.setConductor(conductor);
        viajeRepositorio.editar(viaje);
    }
}