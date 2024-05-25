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

    @Override
    public List<Viaje> filtrarViajesPorDistanciaDelConductor(Double latitudConductor, Double longitudConductor) {
        List<Viaje> todosLosViajes = this.viajeRepositorio.obtenerLasSolicitudesDeViajesPendientes();
        List<Viaje> viajesCercanos = new ArrayList<>();
        double distanciaAFiltar = 5.0;

        for (Viaje viaje : todosLosViajes) {
            double distancia = calcularDistanciaHaversine(latitudConductor, longitudConductor, viaje.getLatitudDeSalida(), viaje.getLongitudDeSalida());
            if (distancia < distanciaAFiltar) {
                viajesCercanos.add(viaje);
            }
        }

        return viajesCercanos;
    }

    @Override
    public void descartarViaje(Integer idViaje) {
        Viaje viaje = this.viajeRepositorio.obtenerViajePorId(idViaje);
        viaje.setDescartado(true);
        this.viajeRepositorio.editar(viaje);
    }

    private double calcularDistanciaHaversine(double latitudConductor, double longitudConductor, double latitudDestino, double longitudDestino) {
        final int RADIO_TIERRA = 6371;

        double diferenciaLatitud = Math.toRadians(latitudDestino - latitudConductor);
        double diferenciaLongitud = Math.toRadians(longitudDestino - longitudConductor);

        // Cálculo del cuadrado de la mitad del ángulo central entre los puntos
        double mitadAnguloCentralCuadrado = Math.sin(diferenciaLatitud / 2) * Math.sin(diferenciaLatitud / 2) +
                Math.cos(Math.toRadians(latitudConductor)) * Math.cos(Math.toRadians(latitudDestino)) *
                        Math.sin(diferenciaLongitud / 2) * Math.sin(diferenciaLongitud / 2);

        // Cálculo del ángulo central entre los puntos
        double anguloCentral = 2 * Math.atan2(Math.sqrt(mitadAnguloCentralCuadrado), Math.sqrt(1 - mitadAnguloCentralCuadrado));

        // Cálculo de la distancia entre los puntos utilizando la fórmula de Haversine
        return RADIO_TIERRA * anguloCentral;
    }
}