package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.ClienteRepository;
import com.tallerwebi.dominio.ClienteService;
import com.tallerwebi.dominio.Paquete;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ClienteServiceImpl implements ClienteService {

    private ClienteRepository clienteRepository;

    @Autowired
    public ClienteServiceImpl(ClienteRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
    }

    @Override
    public Paquete buscarPaquete(Integer idPaquete) {
        return this.clienteRepository.buscarPaquete(idPaquete);
    }

    @Override
    public Double calcularEnvio(String domicilioSalida, String domicilioEntrega) {
        //Hacer la logica
        return 0.0;
    }

    @Override
    public void guardarPaquete(Paquete paqueteActual) {
        this.clienteRepository.guardarPaquete(paqueteActual);
    }
}
