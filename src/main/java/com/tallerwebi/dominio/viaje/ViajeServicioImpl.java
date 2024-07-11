package com.tallerwebi.dominio.viaje;

import com.tallerwebi.dominio.cliente.Cliente;
import com.tallerwebi.dominio.conductor.Conductor;
import com.tallerwebi.dominio.enums.TipoEstado;
import com.tallerwebi.dominio.exceptions.*;
import com.tallerwebi.dominio.paquete.Paquete;
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
    public DatosViaje obtenerViajeAceptadoPorId(Integer id) throws ViajeNoEncontradoException {
        if (id == null || id <= 0) {
            throw new ViajeNoEncontradoException("El ID no es valido");
        }

        Viaje viaje = viajeRepositorio.obtenerViajePorId(id);

        if (viaje == null) {
            throw new ViajeNoEncontradoException("No se encontro el viaje");
        }

        DatosViaje datosViaje = new DatosViaje();

        return datosViaje.toDatosViaje(viaje);
    }

    @Override
    public List<DatosViaje> obtenerHistorialDeViajesConductor(Conductor conductor) throws UsuarioNoEncontradoException {
        if (conductor == null) {
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
    public List<Viaje> obtenerViajesEnProceso(Conductor conductor) throws UsuarioNoEncontradoException {
        if (conductor == null) {
            throw new UsuarioNoEncontradoException("No se encuentra logueado");
        }

        List<Viaje> viajes = viajeRepositorio.obtenerViajesPorConductor(conductor);
        List<Viaje> viajesEnProceso = new ArrayList<>();

        for (Viaje viaje : viajes) {
            if (viaje.getEstado() != TipoEstado.CANCELADO && viaje.getEstado() != TipoEstado.TERMINADO && viaje.getEstado() != TipoEstado.DESCARTADO && viaje.getEstado() != null && viaje.getEstado() != TipoEstado.PENDIENTE) {
                viajesEnProceso.add(viaje);
            }
        }
        return viajesEnProceso;
    }

    @Override
    public void aceptarViaje(DatosViaje datosViaje, Conductor conductor) throws UsuarioNoEncontradoException, ViajeNoEncontradoException {
        if (conductor == null) {
            throw new UsuarioNoEncontradoException("No se encuentra logueado");
        }

        if (datosViaje == null) {
            throw new ViajeNoEncontradoException("Los datos son nulos");
        }

        Viaje viajeAceptadoActual = this.viajeRepositorio.obtenerViajePorId(datosViaje.getIdViaje());

        if (viajeAceptadoActual == null) {
            throw new ViajeNoEncontradoException("El viaje no existe");
        }

        viajeAceptadoActual.setConductor(conductor);
        viajeAceptadoActual.setEstado(TipoEstado.ACEPTADO);
        viajeRepositorio.editar(viajeAceptadoActual);
    }

    @Override
    public void cancelarViaje(Viaje viaje) throws ViajeNoEncontradoException {
        if (viaje == null) {
            throw new ViajeNoEncontradoException("Los datos son nulos");
        }

        Viaje viajeAceptadoActual = this.viajeRepositorio.obtenerViajePorId(viaje.getId());

        if (viajeAceptadoActual == null) {
            throw new ViajeNoEncontradoException("El viaje no existe");
        }

        viajeAceptadoActual.setCanceladoPor(viaje.getConductor().getId());
        viajeAceptadoActual.setEstado(TipoEstado.CANCELADO);
        viajeAceptadoActual.setFecha(LocalDateTime.now());
        viajeAceptadoActual.setAfectaPenalizacion(true);
        viajeRepositorio.editar(viajeAceptadoActual);
    }

    @Override
    public void terminarViaje(DatosViaje datosViaje) throws ViajeNoEncontradoException {
        if (datosViaje == null) {
            throw new ViajeNoEncontradoException("Los datos son nulos");
        }

        Viaje viajeAceptadoActual = this.viajeRepositorio.obtenerViajePorId(datosViaje.getIdViaje());

        if (viajeAceptadoActual == null) {
            throw new ViajeNoEncontradoException("El viaje no existe");
        }

        viajeAceptadoActual.setEstado(TipoEstado.TERMINADO);
        viajeAceptadoActual.setFecha(LocalDateTime.now());
        viajeRepositorio.editar(viajeAceptadoActual);
    }

    @Override
    public Viaje crearViaje(Cliente cliente, Viaje viaje, Paquete paquete) throws ViajeNoEncontradoException, ClienteNoEncontradoException, PaqueteNoEncontradoException, PrecioInvalidoException {
        if (cliente == null) {
            throw new ClienteNoEncontradoException("No esta logueado");
        }

        if (viaje == null) {
            throw new ViajeNoEncontradoException("Los datos son nulos");
        }

        if (paquete == null) {
            throw new PaqueteNoEncontradoException("No se encontró el paquete buscado");
        }

        viaje.setCliente(cliente);
        viaje.setPaquete(paquete);
        viaje.setEstado(TipoEstado.PENDIENTE);
        Double precio = this.calcularPrecio(viaje);

        if (precio == null || precio <= 0) {
            throw new PrecioInvalidoException("El precio es invalido o menor a 0");
        }

        viaje.setPrecio(precio);
        viaje.setFecha(LocalDateTime.now());
        return this.viajeRepositorio.guardarViaje(viaje);
    }

    @Override
    public Viaje buscarViaje(Integer idViaje) throws ViajeNoEncontradoException {
        if (idViaje == null || idViaje <= 0) {
            throw new ViajeNoEncontradoException("ID Invalido");
        }

        Viaje viajeObtenido = this.viajeRepositorio.obtenerViajePorId(idViaje);

        if (viajeObtenido == null) {
            throw new ViajeNoEncontradoException("Viaje no encontrado");
        }

        return viajeObtenido;
    }

    @Override
    public List<Viaje> obtenerViajesEnProcesoDelCliente(Integer idUsuario) throws ClienteNoEncontradoException {
        if (idUsuario == null || idUsuario <= 0) {
            throw new ClienteNoEncontradoException("ID Invalido");
        }

        List<Viaje> viajes = this.viajeRepositorio.obtenerViajesPorCliente(idUsuario);
        List<Viaje> viajesVista = new ArrayList<>();

        for (Viaje viajeObtenido : viajes) {
            if(viajeObtenido.getEstado().equals(TipoEstado.PENDIENTE) || viajeObtenido.getEstado().equals(TipoEstado.ACEPTADO)){
                viajesVista.add(viajeObtenido);
            }
        }

        return viajesVista;
    }

    @Override
    public void cancelarEnvio(Viaje viaje) throws ViajeNoEncontradoException {
        if (viaje == null) {
            throw new ViajeNoEncontradoException("No se pudo cancelar el viaje");
        }

        viaje.setCanceladoPor(viaje.getCliente().getId());
        viaje.setFecha(LocalDateTime.now());
        viaje.setEstado(TipoEstado.CANCELADO);
        this.viajeRepositorio.editar(viaje);
    }

    @Override
    public List<Viaje> obtenerViajesCanceladosDelCliente(Integer idUsuario) throws ClienteNoEncontradoException {
        if (idUsuario == null || idUsuario <= 0) {
            throw new ClienteNoEncontradoException("ID Invalido");
        }

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
    public Viaje obtenerViajePorId(Integer idViaje) throws ViajeNoEncontradoException {
        if (idViaje == null || idViaje <= 0) {
            throw new ViajeNoEncontradoException("ID Invalido");
        }

        return this.viajeRepositorio.obtenerViajePorId(idViaje);
    }

    @Override
    public void duplicarViajeCancelado(Viaje viajeObtenido) throws ViajeNoEncontradoException {
        if (viajeObtenido == null) {
            throw new ViajeNoEncontradoException("Los datos son nulos");
        }

        viajeObtenido.setFecha(LocalDateTime.now());
        viajeObtenido.setEstado(TipoEstado.PENDIENTE);
        viajeObtenido.setCanceladoPor(null);
        viajeObtenido.setConductor(null);
        viajeObtenido.setEnviadoNuevamente(false);
        this.viajeRepositorio.guardarViajeDuplicado(viajeObtenido);
    }

    @Override
    public void duplicarViajeDescartado(Viaje viajeObtenido, Conductor conductor) throws ClienteNoEncontradoException, ViajeNoEncontradoException {
        if (viajeObtenido == null) {
            throw new ViajeNoEncontradoException("Los datos son nulos");
        }

        if (conductor == null) {
            throw new ClienteNoEncontradoException("No esta logueado");
        }

        viajeObtenido.setFecha(LocalDateTime.now());
        viajeObtenido.setEstado(TipoEstado.DESCARTADO);
        viajeObtenido.setConductor(conductor);
        viajeObtenido.setAfectaPenalizacion(true);
        this.viajeRepositorio.guardarViajeDuplicado(viajeObtenido);
    }

    @Override
    public void noDuplicarViaje(Viaje viajeObtenido) throws ViajeNoEncontradoException {
        if (viajeObtenido == null) {
            throw new ViajeNoEncontradoException("Los datos son nulos");
        }

        viajeObtenido.setEnviadoNuevamente(false);
        this.viajeRepositorio.editar(viajeObtenido);
    }

    @Override
    public void actualizarViajeCancelado(Viaje viajeObtenido) throws ViajeNoEncontradoException {
        if (viajeObtenido == null) {
            throw new ViajeNoEncontradoException("Los datos son nulos");
        }

        viajeObtenido.setEnviadoNuevamente(true);
        this.viajeRepositorio.editar(viajeObtenido);
    }

    @Override
    public List<Viaje> obtenerHistorialDeEnvios(Integer idCliente) throws ClienteNoEncontradoException {
        if (idCliente == null || idCliente <= 0) {
            throw new ClienteNoEncontradoException("ID Invalido");
        }

        List<Viaje> viajesObtenidos = this.viajeRepositorio.obtenerViajesPorCliente(idCliente);
        List<Viaje> historialDeEnvios = new ArrayList<>();

        for(Viaje viaje : viajesObtenidos){
            if(!viaje.getEstado().equals(TipoEstado.PENDIENTE) && viaje.getEnviadoNuevamente() != null && !viaje.getEnviadoNuevamente()){
                historialDeEnvios.add(viaje);
            }
        }

        return historialDeEnvios;
    }

    @Override
    public void actualizarViaje(Viaje viaje) throws ViajeNoEncontradoException {
        if (viaje == null) {
            throw new ViajeNoEncontradoException("El viaje no existe");
        }

        viajeRepositorio.editar(viaje);
    }

    @Override
    public List<DatosViaje> filtrarViajesPorDistanciaDelConductor(Double latitudConductor, Double longitudConductor, Double distanciaAFiltrar, Conductor conductor) throws UsuarioNoEncontradoException, CoordenadasNoEncontradasException {
        if (conductor == null) {
            throw new UsuarioNoEncontradoException("No esta logueado");
        }

        if (latitudConductor == null || longitudConductor == null) {
            throw new CoordenadasNoEncontradasException("Coordenadas del usuario no encontradas");
        }

        List<Viaje> viajes;

        if (distanciaAFiltrar == null || distanciaAFiltrar == 0.0) {
            viajes = this.viajeRepositorio.traerTodosLosViajesPendientes();
        } else if (distanciaAFiltrar < 0 || distanciaAFiltrar > 10.0) {
            throw new CoordenadasNoEncontradasException("Distancia invalida");
        } else {
            viajes = this.viajeRepositorio.encontrarViajesCercanos(latitudConductor, longitudConductor, distanciaAFiltrar);
        }

        List<Viaje> viajesDescartados = this.viajeRepositorio.traerTodosLosViajesDescartadosPorConductor(conductor);

        List<Viaje> viajesAFiltrar = filtrarViajesDuplicados(viajes, viajesDescartados);

        List<Viaje> viajesAMostrar = calcularLaDistanciaDelViajeEntreLaSalidaYElDestino(viajesAFiltrar);

        List<Viaje> viajesFiltradosDPD = this.compararPesosYDimesionesDeViajes(viajesAMostrar, conductor);

        DatosViaje datosViaje = new DatosViaje();

        return viajesFiltradosDPD.stream().limit(5).map(viaje -> datosViaje.toDatosViaje(viaje)).collect(Collectors.toList());
    }

    public List<Viaje> filtrarViajesDuplicados(List<Viaje> viajes, List<Viaje> viajesDescartados) {
        return viajes.stream()
                .filter(viaje -> viajesDescartados.stream().noneMatch(viajeDescartado ->
                        viaje.getPaquete().getId().equals(viajeDescartado.getPaquete().getId()) &&
                                viaje.getDomicilioDeSalida().equals(viajeDescartado.getDomicilioDeSalida()) &&
                                viaje.getDomicilioDeLlegada().equals(viajeDescartado.getDomicilioDeLlegada())))
                .collect(Collectors.toList());
    }

    public List<Viaje> compararPesosYDimesionesDeViajes(List<Viaje> viajesAMostrar, Conductor conductor) {
        List <Viaje> filtrados=new ArrayList<>();

        for ( Viaje viaje :viajesAMostrar){
            if(viaje.getPaquete().getPeso()<conductor.getVehiculo().getPesoSoportado()&&viaje.getPaquete().getDimension()<conductor.getVehiculo().getDimensionDisponible()){
                filtrados.add(viaje);
            }
        }
        return filtrados;
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

    public Double calcularPrecio(Viaje viaje){
        List<Viaje> viajeACalcularDistancia = new ArrayList<>();
        viajeACalcularDistancia.add(viaje);

        Double distancia = (this.calcularLaDistanciaDelViajeEntreLaSalidaYElDestino(viajeACalcularDistancia)).get(0).getDistanciaDelViaje();
        Double peso = viaje.getPaquete().getPeso();
        Double dimension = viaje.getPaquete().getDimension();

        double precioBaseEnvio = 1800.0;
        double precioxKm = 200.0;
        double precioxKg = 100.0;
        double precioxCm = 5.0;

        if(distancia > 3.0){
            precioBaseEnvio += (precioxKm * (Math.round(distancia)) - 3.0);
        }

        if(peso > 3.0){
            precioBaseEnvio += (precioxKg * (Math.round(peso)) - 3.0);
        }

        if(dimension > 40.0) {
            precioBaseEnvio += (precioxCm * (Math.round(dimension)) - 40.0);
        }

        return precioBaseEnvio;
    }
}