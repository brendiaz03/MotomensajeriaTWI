package com.tallerwebi.dominio.viaje;

import com.tallerwebi.dominio.conductor.Conductor;
import com.tallerwebi.dominio.conductor.ConductorNoEncontradoException;
import com.tallerwebi.presentacion.Datos.DatosViaje;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ViajeServicioImpl implements ViajeServicio {

    private final ViajeRepositorio viajeRepositorio;

    @Autowired
    public ViajeServicioImpl(ViajeRepositorio viajeRepositorio){
        this.viajeRepositorio = viajeRepositorio;
    }

    @Override
    public DatosViaje obtenerViajeAceptadoPorId(Integer id) {
        Viaje viaje = viajeRepositorio.obtenerViajePorId(id);
        return mapearViajeADatosViaje(viaje);
    }

    @Override
    public List<DatosViaje> obtenerHistorialDeViajes(Conductor conductor) throws ConductorNoEncontradoException {
        if(conductor == null){
            throw new ConductorNoEncontradoException("No se encuentra logueado");
        }

        List<Viaje> viajes = viajeRepositorio.obtenerViajesPorConductor(conductor);
        List<DatosViaje> historial = new ArrayList<>();
        for (Viaje viaje : viajes) {
            if (viaje.getCancelado() || viaje.getTerminado()) {
                historial.add(mapearViajeADatosViaje(viaje));
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
            if (!viaje.getCancelado() && !viaje.getTerminado() && !viaje.getDescartado()) {
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

        if(distanciaAFiltrar == null){
            List<Viaje> viajes = this.viajeRepositorio.traerTodosLosViajesQueNoEstenAceptados();
            List<Viaje> viajesAMostrar = calcularLaDistanciaDelViajeEntreLaSalidaYElDestino(viajes);
            return viajesAMostrar.stream().limit(5)
                    .map(viaje -> new DatosViaje(viaje.getId(), viaje.getDomicilioDeSalida(), viaje.getDomicilioDeLlegada(), viaje.getCliente().getNombre(), viaje.getPrecio(), viaje.getCodigoPostal(), viaje.getLatitudDeSalida(), viaje.getLongitudDeSalida(), viaje.getLatitudDeLlegada(), viaje.getLongitudDeLlegada(), viaje.getDistanciaDelViaje(), viaje.getTerminado(), viaje.getCancelado(), viaje.getAceptado(), viaje.getDescartado()))
                    .collect(Collectors.toList());
        }

        List<Viaje> viajesCercanos = this.viajeRepositorio.encontrarViajesCercanos(latitudConductor, longitudConductor, distanciaAFiltrar);

        List<Viaje> viajesAMostrar = calcularLaDistanciaDelViajeEntreLaSalidaYElDestino(viajesCercanos);

        return viajesAMostrar.stream().limit(5)
                .map(viaje -> new DatosViaje(viaje.getId(), viaje.getDomicilioDeSalida(), viaje.getDomicilioDeLlegada(), viaje.getCliente().getNombre(), viaje.getPrecio(), viaje.getCodigoPostal(), viaje.getLatitudDeSalida(), viaje.getLongitudDeSalida(), viaje.getLatitudDeLlegada(), viaje.getLongitudDeLlegada(), viaje.getDistanciaDelViaje(), viaje.getTerminado(), viaje.getCancelado(), viaje.getAceptado(), viaje.getDescartado()))
                .collect(Collectors.toList());
    }

    @Override
    public void aceptarViaje(DatosViaje datosViaje, Conductor conductor) {
        Viaje viajeAceptadoActual = this.viajeRepositorio.obtenerViajePorId(datosViaje.getIdViaje());
        viajeAceptadoActual.setConductor(conductor);
        viajeAceptadoActual.setAceptado(true);
        viajeRepositorio.editar(viajeAceptadoActual);
    }

    @Override
    public void cancelarViaje(DatosViaje datosViaje) {
        Viaje viajeAceptadoActual = this.viajeRepositorio.obtenerViajePorId(datosViaje.getIdViaje());
        viajeAceptadoActual.setCancelado(true);
        viajeAceptadoActual.setFecha(LocalDateTime.now());
        viajeRepositorio.editar(viajeAceptadoActual);
    }

    @Override
    public void terminarViaje(DatosViaje datosViaje) {
        Viaje viajeAceptadoActual = this.viajeRepositorio.obtenerViajePorId(datosViaje.getIdViaje());
        viajeAceptadoActual.setTerminado(true);
        viajeAceptadoActual.setFecha(LocalDateTime.now());
        viajeRepositorio.editar(viajeAceptadoActual);
    }

    public List<Viaje> calcularLaDistanciaDelViajeEntreLaSalidaYElDestino(List<Viaje> viajes) {
        final double RADIO_TIERRA = 6371;
        double diferenciaDeLatitud;
        double diferenciaDeLongitud;
        List<Viaje> viajesConDistanciaCalculada = new ArrayList<>();

        for (Viaje viajeACalcular : viajes){
            diferenciaDeLatitud = Math.toRadians(viajeACalcular.getLatitudDeLlegada()) - Math.toRadians(viajeACalcular.getLatitudDeSalida());
            diferenciaDeLongitud = Math.toRadians(viajeACalcular.getLongitudDeLlegada()) - Math.toRadians(viajeACalcular.getLongitudDeSalida());

            double a = Math.sin(diferenciaDeLatitud / 2) * Math.sin(diferenciaDeLatitud / 2) +
                    Math.cos(Math.toRadians(viajeACalcular.getLatitudDeSalida())) *
                            Math.cos(Math.toRadians(viajeACalcular.getLatitudDeLlegada())) *
                            Math.sin(diferenciaDeLongitud / 2) * Math.sin(diferenciaDeLongitud / 2);

            double distanciaFinal = 2 * RADIO_TIERRA * Math.asin(Math.sqrt(a));

            double kilometrosDeDistancia = Math.round(distanciaFinal * 10) / 10.0;

            viajeACalcular.setDistanciaDelViaje(kilometrosDeDistancia);
            viajesConDistanciaCalculada.add(viajeACalcular);
        }
        return viajesConDistanciaCalculada;
    }

    public DatosViaje mapearViajeADatosViajeHistorial(Viaje viaje) {
        return new DatosViaje(viaje.getId(), viaje.getDomicilioDeSalida(), viaje.getDomicilioDeLlegada(), viaje.getCliente().getNombre(), viaje.getPrecio(), viaje.getCodigoPostal(),viaje.getTerminado(), viaje.getCancelado());
    }

    public DatosViaje mapearViajeADatosViaje(Viaje viaje){
        return new DatosViaje(viaje.getId(), viaje.getDomicilioDeSalida(), viaje.getDomicilioDeLlegada(), viaje.getCliente().getNombre(), viaje.getPrecio(), viaje.getCodigoPostal(), viaje.getLatitudDeSalida(), viaje.getLongitudDeSalida(), viaje.getLatitudDeLlegada(), viaje.getLongitudDeLlegada(), viaje.getDistanciaDelViaje(), viaje.getTerminado(), viaje.getCancelado(), viaje.getAceptado(), viaje.getDescartado());
    }
}