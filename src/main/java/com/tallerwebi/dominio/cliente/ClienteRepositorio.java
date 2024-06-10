package com.tallerwebi.dominio.cliente;

import com.tallerwebi.dominio.paquete.Paquete;
import com.tallerwebi.dominio.viaje.Viaje;

public interface ClienteRepositorio {

    Cliente obtenerClientePorId(Integer idusuario);
}
