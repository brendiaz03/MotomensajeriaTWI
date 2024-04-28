package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.entidades.Pago;
import com.tallerwebi.dominio.PagoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PagoController {

    private PagoService pagoService;

    @Autowired
    public PagoController(PagoService pagoService) {
        this.pagoService = pagoService;
    }

    @PostMapping("/generarPago")
    private Pago generarPago(Pago pago) {
        Pago pagoActual = this.pagoService.guardarPago(pago);
        return pagoActual;
    }
}
