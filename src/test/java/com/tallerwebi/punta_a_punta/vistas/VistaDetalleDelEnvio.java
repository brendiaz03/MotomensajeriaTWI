package com.tallerwebi.punta_a_punta.vistas;

import com.microsoft.playwright.Page;

public class VistaDetalleDelEnvio extends VistaWeb{

    public VistaDetalleDelEnvio(Page page) {
        super(page);
        page.navigate("localhost:8080/login");
    }

    public void escribirUsuario(String nombre) {
        this.escribirEnElElemento("#usuario", nombre);
    }

    public void escribirContrasenia(String nombre) {
        this.escribirEnElElemento("#password", nombre);
    }

    public void darClickEnIniciarSesion() {
        this.darClickEnElElemento("#ingresar");
    }

    public void darClickEnHistorialDeEnvios() {
        this.darClickEnElElemento("#historial-envios");
    }

    public void darClickEnVerDetalle(Integer idViaje) {
        this.darClickEnElElemento("#detalle-" + idViaje);
    }
}