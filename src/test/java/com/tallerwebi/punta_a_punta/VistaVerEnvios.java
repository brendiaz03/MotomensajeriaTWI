package com.tallerwebi.punta_a_punta;

import com.microsoft.playwright.*;
import com.tallerwebi.punta_a_punta.vistas.VistaEnvios;
import org.junit.jupiter.api.*;

public class VistaVerEnvios {

    static Playwright playwright;
    static Browser browser;
    BrowserContext context;
    VistaEnvios vistaEnvios;

    @BeforeAll
    static void abrirNavegador() {
        playwright = Playwright.create();
        browser = playwright.chromium().launch();
        browser = playwright.chromium().launch(new BrowserType.LaunchOptions().setHeadless(false).setSlowMo(800));

    }

    @AfterAll
    static void cerrarNavegador() {
        playwright.close();
    }

    @BeforeEach
    void crearContextoYPagina() {
        context = browser.newContext();
        Page page = context.newPage();
        vistaEnvios = new VistaEnvios(page);
    }

    @AfterEach
    void cerrarContexto() {
        context.close();
    }

    @Test
    void verTodosLosEnvios() {
        vistaEnvios.escribirUsuario("Joaco");
        vistaEnvios.escribirContrasenia("123");
        vistaEnvios.darClickEnIniciarSesion();
        vistaEnvios.darClickEnEnviosEnProceso();
    }

    @Test
    void verTodosLosEnviosYCancelarUno() {
        vistaEnvios.escribirUsuario("Joaco");
        vistaEnvios.escribirContrasenia("123");
        vistaEnvios.darClickEnIniciarSesion();
        vistaEnvios.darClickEnEnviosEnProceso();
        vistaEnvios.darClickEnCancelarEnvio(52);
        vistaEnvios.darClickEnHistorialDeEnvios();
    }

    @Test
    void verTodosLosEnviosYVerElDetalleDeUno() {
        vistaEnvios.escribirUsuario("Joaco");
        vistaEnvios.escribirContrasenia("123");
        vistaEnvios.darClickEnIniciarSesion();
        vistaEnvios.darClickEnEnviosEnProceso();
        vistaEnvios.darClickEnVerDetalle(50);
    }

    @Test
    void verHistorialDeEnvios() {
        vistaEnvios.escribirUsuario("Joaco");
        vistaEnvios.escribirContrasenia("123");
        vistaEnvios.darClickEnIniciarSesion();
        vistaEnvios.darClickEnHistorialDeEnvios();
    }
}