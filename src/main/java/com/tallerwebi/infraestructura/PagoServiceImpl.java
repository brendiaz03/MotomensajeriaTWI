package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.entidades.Pago;
import com.tallerwebi.dominio.PagoRepository;
import com.tallerwebi.dominio.PagoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PagoServiceImpl implements PagoService {

    private PagoRepository pagoRepository;

    @Autowired
    public PagoServiceImpl(PagoRepository pagoRepository) {
        this.pagoRepository = pagoRepository;
    }


    @Override
    public Pago guardarPago(Pago pagoActual) {
        return this.pagoRepository.save(pagoActual);
    }
}
