package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.PaqueteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class PaqueteController {

    private PaqueteService paqueteService;

    @Autowired
    public PaqueteController(PaqueteService paqueteService) {
        this.paqueteService = paqueteService;
    }


}
