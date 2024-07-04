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
        DatosViaje datosViaje = new DatosViaje();

        if (id == null || id <= 0) {
            throw new ViajeNoEncontradoException("El ID no es valido");
        }

        try {
            Viaje viaje = viajeRepositorio.obtenerViajePorId(id);
            if (viaje == null) {
                throw new ViajeNoEncontradoException("No se encontro el viaje");
            }
            return datosViaje.toDatosViaje(viaje);
        } catch (ViajeNoEncontradoException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("Ocurrio un error al obtener el viaje", e);
        }
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
        try {
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

        } catch (UsuarioNoEncontradoException | ViajeNoEncontradoException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("Ocurrio un error inesperado al querer aceptar el viaje", e);
        }
    }

    @Override
    public void cancelarViaje(DatosViaje datosViaje) throws ViajeNoEncontradoException {
        try {
            if (datosViaje == null) {
                throw new ViajeNoEncontradoException("Los datos son nulos");
            }

            Viaje viajeAceptadoActual = this.viajeRepositorio.obtenerViajePorId(datosViaje.getIdViaje());

            if (viajeAceptadoActual == null) {
                throw new ViajeNoEncontradoException("El viaje no existe");
            }

            viajeAceptadoActual.setEstado(TipoEstado.CANCELADO);
            viajeAceptadoActual.setFecha(LocalDateTime.now());
            viajeAceptadoActual.setAfectaPenalizacion(true);
            viajeRepositorio.editar(viajeAceptadoActual);

        } catch (ViajeNoEncontradoException e) {
            throw e;
        } catch (Exception e){
            throw new RuntimeException("Ocurrio un error al cancelar el viaje", e);
        }
    }

    @Override
    public void terminarViaje(DatosViaje datosViaje) throws ViajeNoEncontradoException {
        try {
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

        } catch (ViajeNoEncontradoException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("Ocurrio un problema al querer terminar un viaje");
        }
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
            throw new PaqueteNoEncontradoException();
        }

        try {
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
        } catch (PrecioInvalidoException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("Ocurrio un error inesperado al crear el viaje");
        }
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
            if(viajeObtenido.getEstado().equals(TipoEstado.PENDIENTE)){
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

        try {
            viaje.setFecha(LocalDateTime.now());
            viaje.setEstado(TipoEstado.CANCELADO);
            this.viajeRepositorio.editar(viaje);
        } catch (Exception e) {
            throw new RuntimeException("Ocurrió un error al cancelar el viaje", e);
        }
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

        try {
            viajeObtenido.setFecha(LocalDateTime.now());
            viajeObtenido.setEstado(TipoEstado.PENDIENTE);
            viajeObtenido.setCanceladoPor(null);
            viajeObtenido.setConductor(null);
            viajeObtenido.setEnviadoNuevamente(false);
            this.viajeRepositorio.guardarViajeDuplicado(viajeObtenido);
        } catch (Exception e) {
            throw new RuntimeException("Ocurrio un error inesperado al realizar nuevamente el viaje");
        }
    }

    @Override
    public void duplicarViajeDescartado(Viaje viajeObtenido, Conductor conductor) throws ClienteNoEncontradoException, ViajeNoEncontradoException {
        if (viajeObtenido == null) {
            throw new ViajeNoEncontradoException("Los datos son nulos");
        }

        if (conductor == null) {
            throw new ClienteNoEncontradoException("No esta logueado");
        }

        try {
            viajeObtenido.setFecha(LocalDateTime.now());
            viajeObtenido.setEstado(TipoEstado.DESCARTADO);
            viajeObtenido.setConductor(conductor);
            viajeObtenido.setAfectaPenalizacion(true);
            this.viajeRepositorio.guardarViajeDuplicado(viajeObtenido);
        } catch (Exception e) {
            throw new RuntimeException("Ocurrio un error al descartar el viaje");
        }
    }

    @Override
    public void noDuplicarViaje(Viaje viajeObtenido) throws ViajeNoEncontradoException {
        if (viajeObtenido == null) {
            throw new ViajeNoEncontradoException("Los datos son nulos");
        }

        try {
            viajeObtenido.setEnviadoNuevamente(false);
            this.viajeRepositorio.editar(viajeObtenido);
        } catch (Exception e) {
            throw new RuntimeException("Ocurrio un error al editar el viaje");
        }
    }

    @Override
    public void actualizarViajeCancelado(Viaje viajeObtenido) throws ViajeNoEncontradoException {
        if (viajeObtenido == null) {
            throw new ViajeNoEncontradoException("Los datos son nulos");
        }

        try {
            viajeObtenido.setEnviadoNuevamente(true);
            this.viajeRepositorio.editar(viajeObtenido);
        } catch (Exception e) {
            throw new RuntimeException("Ocurrio un error al actualizar el viaje");
        }
    }

    @Override
    public List<Viaje> obtenerHistorialDeEnvios(Integer idCliente) throws ClienteNoEncontradoException {
        if (idCliente == null || idCliente == 0) {
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
    public List<DatosViaje> filtrarViajesPorDistanciaDelConductor(Double latitudConductor, Double longitudConductor, Double distanciaAFiltrar, Conductor conductor) throws UsuarioNoEncontradoException, CoordenadasNoEncontradasException {
        if (conductor == null) {
            throw new UsuarioNoEncontradoException("No esta logueado");
        }

        if (latitudConductor == null || longitudConductor == null) {
            throw new CoordenadasNoEncontradasException("Coordenadas del usuario no encontradas");
        }

        try {
            List<Viaje> viajes;

            if (distanciaAFiltrar == null) {
                viajes = this.viajeRepositorio.traerTodosLosViajesPendientes();
                System.out.println("VIAJES PENDIENTES TOTALES: " + viajes.size());
            } else if (distanciaAFiltrar < 0) {
                return new ArrayList<>();
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
            return viajesFiltradosDPD.stream().limit(5).map(viaje -> datosViaje.toDatosViaje(viaje)).collect(Collectors.toList());
        } catch (Exception e) {
            throw new RuntimeException("Ocurrio un error al filtrar los viajes");
        }
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
        double precioxKm = 400.0;
        double precioxKg = 200.0;
        double precioxCm = 30.0;

        if(distancia > 3.0){
            precioBaseEnvio += (precioxKm * (Math.round(distancia)) - 3.0);
        }

        if(peso > 2.0){
            precioBaseEnvio += (precioxKg * (Math.round(peso)) - 2.0);
        }

        if(dimension > 30.0) {
            precioBaseEnvio += (precioxCm * (Math.round(dimension)) - 30.0);
        }

        return precioBaseEnvio;
    }
}