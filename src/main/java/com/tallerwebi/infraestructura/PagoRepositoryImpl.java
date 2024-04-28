package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.entidades.Pago;
import com.tallerwebi.dominio.PagoRepository;
import org.springframework.stereotype.Repository;

@Repository
public class PagoRepositoryImpl implements PagoRepository {
    @Override
    public Pago save(Pago pagoActual) {
        return null;
    }
}
