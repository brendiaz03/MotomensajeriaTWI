package com.tallerwebi.dominio.cliente;

import com.tallerwebi.dominio.paquete.Paquete;
import com.tallerwebi.dominio.viaje.Viaje;

public interface ClienteServicio {
    void crearViaje(Cliente cliente, Viaje viaje);

    Cliente obtenerClientePorId(Integer idusuario);

    void guardarPaquete(Paquete paquete);
}
