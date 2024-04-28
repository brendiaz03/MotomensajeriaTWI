package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.PaqueteRepository;
import com.tallerwebi.dominio.PaqueteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PaqueteServiceImpl implements PaqueteService {

    private PaqueteRepository paqueteRepository;

    @Autowired
    public PaqueteServiceImpl (PaqueteRepository paqueteRepository){
        this.paqueteRepository = paqueteRepository;
    }
}
