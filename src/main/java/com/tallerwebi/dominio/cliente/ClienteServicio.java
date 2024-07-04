package com.tallerwebi.dominio.cliente;

import com.tallerwebi.dominio.exceptions.UsuarioNoEncontradoException;
import com.tallerwebi.dominio.paquete.Paquete;
import com.tallerwebi.dominio.viaje.Viaje;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface ClienteServicio {
    Cliente obtenerClientePorId(Integer idusuario) throws UsuarioNoEncontradoException;
}
