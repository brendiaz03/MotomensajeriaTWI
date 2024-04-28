package com.tallerwebi.dominio;

import com.tallerwebi.dominio.entidades.Pago;

public interface PagoRepository {
    Pago save(Pago pagoActual);
}
