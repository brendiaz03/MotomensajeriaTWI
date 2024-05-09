package com.tallerwebi.punta_a_punta.vistas;

import com.microsoft.playwright.Page;

public class VistaViaje extends VistaWeb {

    public VistaViaje(Page page) {
        super(page);
        page.navigate("localhost:8080/spring/viajes");
    }

    public String obtenerDireccionDeSalidaDelViaje(){
        return this.obtenerTextoDelElemento("#direccion-salida");
    }

    public String obtenerDireccionDeLlegadaDelViaje(){
        return this.obtenerTextoDelElemento("#direccion-llegada");
    }

    public String obtenerTitulo() {
        return this.obtenerTextoDelElemento("#titulo");
    }

    public String obtenerNombreDelCliente() {
        return this.obtenerTextoDelElemento("#nombre-cliente");
    }
}
