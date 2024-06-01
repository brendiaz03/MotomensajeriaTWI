package com.tallerwebi.dominio.cliente;

import com.tallerwebi.dominio.viaje.Viaje;
import com.tallerwebi.presentacion.Datos.DatosRegistro;

public interface ClienteServicio {
    void crearViaje(Cliente cliente, Viaje viaje);

    Cliente registrarClienteNoDuplicado(DatosRegistro cliente);
}
