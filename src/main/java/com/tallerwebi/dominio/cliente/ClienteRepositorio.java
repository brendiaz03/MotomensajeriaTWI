package com.tallerwebi.dominio.cliente;

import com.tallerwebi.dominio.paquete.Paquete;
import com.tallerwebi.dominio.viaje.Viaje;

public interface ClienteRepositorio {
    void crearViaje(Viaje viaje);

    Viaje buscarViajePorId(Integer id);

    Cliente registrarCliente(Cliente cliente);

    Cliente buscarDuplicados(String email, String nombreUsuario);

    Cliente obtenerClientePorId(Integer idusuario);

    void guardarPaquete(Paquete paquete);
}
