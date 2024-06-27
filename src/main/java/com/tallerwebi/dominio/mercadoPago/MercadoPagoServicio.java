package com.tallerwebi.dominio.mercadoPago;

import java.io.IOException;

public interface MercadoPagoServicio {

    String pagarViajeMp(Double precioDelViaje) throws IOException;


}
