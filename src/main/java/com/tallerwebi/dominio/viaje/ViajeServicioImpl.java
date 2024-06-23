package com.tallerwebi.dominio.viaje;

import com.tallerwebi.dominio.cliente.Cliente;
import com.tallerwebi.dominio.conductor.Conductor;
import com.tallerwebi.dominio.enums.TipoEstado;
import com.tallerwebi.dominio.paquete.Paquete;
import com.tallerwebi.dominio.exceptions.UsuarioNoEncontradoException;
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
        DatosViaje datosViaje = new DatosViaje();
        Viaje viaje = viajeRepositorio.obtenerViajePorId(id);
        return datosViaje.toDatosViaje(viaje);
    }

    @Override
    public List<DatosViaje> obtenerHistorialDeViajesConductor(Conductor conductor) throws UsuarioNoEncontradoException {
        if(conductor == null){
            throw new UsuarioNoEncontradoException("No se encuentra logueado");
        }

        List<Viaje> viajes = viajeRepositorio.obtenerViajesPorConductor(conductor);
        List<DatosViaje> historial = new ArrayList<>();
        for (Viaje viaje : viajes) {
            DatosViaje datosViaje = new DatosViaje();
            if (viaje.getEstado() == TipoEstado.CANCELADO || viaje.getEstado() == TipoEstado.TERMINADO) {
               historial.add(datosViaje.toDatosViajeHistorial(viaje));
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
            if (viaje.getEstado() != TipoEstado.CANCELADO && viaje.getEstado() != TipoEstado.TERMINADO && viaje.getEstado() != TipoEstado.DESCARTADO) {
                viajesEnProceso.add(viaje);
            }
        }
        return viajesEnProceso;
    }

    @Override
    public void descartarViaje(Integer idViaje, Conductor conductor) {
        Viaje viaje = this.viajeRepositorio.obtenerViajePorId(idViaje);
        viaje.setEstado(TipoEstado.DESCARTADO);
        viaje.setConductor(conductor);
        this.viajeRepositorio.editar(viaje);
    }

    @Override
    public Boolean estaPenalizado(Conductor conductor) {
        List<Viaje> viajesObtenidos = this.viajeRepositorio.obtenerViajesPorConductor(conductor);
        List<Viaje> viajesDescartados = new ArrayList<>();
        boolean isPenalizado = false;
        for (Viaje viaje : viajesObtenidos) {
            if(viaje.getEstado() == TipoEstado.DESCARTADO){
                viajesDescartados.add(viaje);
            }
        }

        if(viajesDescartados.size() >= 5){
            isPenalizado = true;
        }

        return isPenalizado;
    }

//

    @Override
    public List<DatosViaje> filtrarViajesPorDistanciaDelConductor(Double latitudConductor, Double longitudConductor, Double distanciaAFiltrar, Conductor conductor) {
        List<Viaje> viajes;
        if (distanciaAFiltrar == null) {
            viajes = this.viajeRepositorio.traerTodosLosViajesPendientes();
            System.out.println("VIAJES PENDIENTES TOTALES: " + viajes.size());
        } else {
            viajes = this.viajeRepositorio.encontrarViajesCercanos(latitudConductor, longitudConductor, distanciaAFiltrar);
        }

        List<Viaje> viajesDescartados = this.viajeRepositorio.traerTodosLosViajesDescartadosPorConductor(conductor);
        System.out.println("VIAJES DESCARTADOS TOTALES: " + viajesDescartados.size());

        List<Viaje> viajesAFiltrar = filtrarViajesDuplicados(viajes, viajesDescartados);
        System.out.println("VIAJES A FILTRAR TOTALES: " + viajesAFiltrar.size());

        List<Viaje> viajesAMostrar = calcularLaDistanciaDelViajeEntreLaSalidaYElDestino(viajesAFiltrar);
        List<Viaje> viajesFiltradosDPD = this.compararPesosYDimesionesDeViajes(viajesAMostrar, conductor);

        DatosViaje datosViaje = new DatosViaje();
        return viajesFiltradosDPD.stream().limit(5)
                .map(viaje -> datosViaje.toDatosViaje(viaje))
                .collect(Collectors.toList());
    }

    private List<Viaje> filtrarViajesDuplicados(List<Viaje> viajes, List<Viaje> viajesDescartados) {
        return viajes.stream()
                .filter(viaje -> viajesDescartados.stream().noneMatch(viajeDescartado ->
                        viaje.getPaquete().getId().equals(viajeDescartado.getPaquete().getId()) &&
                                viaje.getDomicilioDeSalida().equals(viajeDescartado.getDomicilioDeSalida()) &&
                                viaje.getDomicilioDeLlegada().equals(viajeDescartado.getDomicilioDeLlegada())))
                .collect(Collectors.toList());
    }


    private List<Viaje> compararPesosYDimesionesDeViajes(List<Viaje> viajesAMostrar, Conductor conductor) {
        List <Viaje> filtrados=new ArrayList<>();

        for ( Viaje viaje :viajesAMostrar){
             if(viaje.getPaquete().getPeso()<conductor.getVehiculo().getPesoSoportado()&&viaje.getPaquete().getDimension()<conductor.getVehiculo().getDimensionDisponible()){
                 filtrados.add(viaje);
             }
        }
        return filtrados;
    }

    @Override
    public void aceptarViaje(DatosViaje datosViaje, Conductor conductor) {
        Viaje viajeAceptadoActual = this.viajeRepositorio.obtenerViajePorId(datosViaje.getIdViaje());

            viajeAceptadoActual.setConductor(conductor);
            viajeAceptadoActual.setEstado(TipoEstado.ACEPTADO);
            viajeRepositorio.editar(viajeAceptadoActual);
    }



    @Override
    public void cancelarViaje(DatosViaje datosViaje) {
        Viaje viajeAceptadoActual = this.viajeRepositorio.obtenerViajePorId(datosViaje.getIdViaje());
        viajeAceptadoActual.setEstado(TipoEstado.CANCELADO);
        viajeAceptadoActual.setFecha(LocalDateTime.now());
        viajeRepositorio.editar(viajeAceptadoActual);
    }

    @Override
    public void terminarViaje(DatosViaje datosViaje) {
        Viaje viajeAceptadoActual = this.viajeRepositorio.obtenerViajePorId(datosViaje.getIdViaje());
        viajeAceptadoActual.setEstado(TipoEstado.TERMINADO);
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

    @Override
    public Viaje crearViaje(Cliente cliente, Viaje viaje, Paquete paquete) {
        viaje.setCliente(cliente);
        viaje.setPaquete(paquete);
        viaje.setEstado(TipoEstado.PENDIENTE);
        viaje.setPrecio(this.calcularPrecio(viaje));
        viaje.setFecha(LocalDateTime.now());
       return this.viajeRepositorio.guardarViaje(viaje);
    }

    @Override
    public Viaje buscarViaje(Integer idViaje){
        return this.viajeRepositorio.obtenerViajePorId(idViaje);

    }

    @Override
    public List<Viaje> obtenerViajesEnProcesoDelCliente(Integer idUsuario) {
        List<Viaje> viajes = this.viajeRepositorio.obtenerViajesPorCliente(idUsuario);
        List<Viaje> viajesVista = new ArrayList<>();


        for (Viaje viajeObtenido : viajes) {
            if(viajeObtenido.getEstado().equals(TipoEstado.PENDIENTE)){
                viajesVista.add(viajeObtenido);
            }
        }

        return viajesVista;
    }

    @Override
    public void cancelarEnvío(Viaje viaje) {
        viaje.setFecha(LocalDateTime.now());
        viaje.setEstado(TipoEstado.CANCELADO);
        this.viajeRepositorio.editar(viaje);
    }

    @Override
    public List<Viaje> obtenerViajesCanceladosDelCliente(Integer idUsuario) {
        List<Viaje> viajesObtenidos = this.viajeRepositorio.obtenerViajesPorCliente(idUsuario);
        List<Viaje> viajesCancelados = new ArrayList<>();

        for(Viaje viaje : viajesObtenidos){
            if(viaje.getEstado().equals(TipoEstado.CANCELADO) && viaje.getCanceladoPor() != idUsuario && viaje.getEnviadoNuevamente() == null){
                viajesCancelados.add(viaje);
            }
        }

        return viajesCancelados;
    }

    @Override
    public Viaje obtenerViajePorId(Integer idViaje) {
        return this.viajeRepositorio.obtenerViajePorId(idViaje);
    }

    @Override
    public void duplicarViajeCancelado(Viaje viajeObtenido) {
        viajeObtenido.setFecha(LocalDateTime.now());
        viajeObtenido.setEstado(TipoEstado.PENDIENTE);
        viajeObtenido.setCanceladoPor(null);
        viajeObtenido.setConductor(null);
        viajeObtenido.setEnviadoNuevamente(false);
        this.viajeRepositorio.guardarViajeDuplicado(viajeObtenido);
    }
    @Override
    public void duplicarViajeDescartado(Viaje viajeObtenido, Conductor conductor) {
        viajeObtenido.setFecha(LocalDateTime.now());
        viajeObtenido.setEstado(TipoEstado.DESCARTADO);
        viajeObtenido.setConductor(conductor);
        viajeObtenido.setAfectaPenalizacion(true);
        this.viajeRepositorio.guardarViajeDuplicado(viajeObtenido);
    }

    @Override
    public List<Viaje> buscarDescartadosPorConductor(Conductor conductor) {
       return this.viajeRepositorio.traerTodosLosViajesDescartadosPorConductor(conductor);
    }

    @Override
    public void noDuplicarViaje(Viaje viajeObtenido) {
        viajeObtenido.setEnviadoNuevamente(false);
        this.viajeRepositorio.editar(viajeObtenido);
    }

    @Override
    public void actualizarViajeCancelado(Viaje viajeObtenido) {
        viajeObtenido.setEnviadoNuevamente(true);
        this.viajeRepositorio.editar(viajeObtenido);
    }

    @Override
    public List<Viaje> obtenerHistorialDeEnvios(Integer idCliente) {
        List<Viaje> viajesObtenidos = this.viajeRepositorio.obtenerViajesPorCliente(idCliente);
        List<Viaje> historialDeEnvios = new ArrayList<>();

        for(Viaje viaje : viajesObtenidos){
            if(!viaje.getEstado().equals(TipoEstado.PENDIENTE) && viaje.getEnviadoNuevamente() != null && !viaje.getEnviadoNuevamente()){
                historialDeEnvios.add(viaje);
            }
        }

        return historialDeEnvios;

    }

    private Double calcularPrecio (Viaje viaje){

    List<Viaje> viajeACalcularDistancia = new ArrayList<>();
    viajeACalcularDistancia.add(viaje);

    Double distancia =(this.calcularLaDistanciaDelViajeEntreLaSalidaYElDestino(viajeACalcularDistancia)).get(0).getDistanciaDelViaje();
    Double peso= viaje.getPaquete().getPeso();
    Double dimension= viaje.getPaquete().getDimension();

    Double precioBaseEnvio=1800.0;
    Double precioxKm=400.0;
    Double precioxKg=200.0;
    Double precioxCm =30.0;


    if(distancia>3.0){
        precioBaseEnvio+=(precioxKm*(Math.round(distancia))-3.0);
    }

    if(peso>2.0){
        precioBaseEnvio+=(precioxKg*(Math.round(peso))-2.0);
    }

    if(dimension>30.0) {
        precioBaseEnvio+=(precioxCm*(Math.round(dimension))-30.0);
    }

    return precioBaseEnvio;
}

}
