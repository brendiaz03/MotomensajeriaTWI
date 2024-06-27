package com.tallerwebi.punta_a_punta;

import com.microsoft.playwright.*;
import com.tallerwebi.punta_a_punta.vistas.VistaDetalleDelEnvio;
import org.junit.jupiter.api.*;

public class VistaVerDetalleDelEnvio {

    static Playwright playwright;
    static Browser browser;
    BrowserContext context;
    VistaDetalleDelEnvio vistaDetalleDelEnvio;

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
        vistaDetalleDelEnvio = new VistaDetalleDelEnvio(page);
    }

    @AfterEach
    void cerrarContexto() {
        context.close();
    }

    @Test
    void verDetalleDelEnvio() {
        vistaDetalleDelEnvio.escribirUsuario("Joaco");
        vistaDetalleDelEnvio.escribirContrasenia("123");
        vistaDetalleDelEnvio.darClickEnIniciarSesion();
        vistaDetalleDelEnvio.darClickEnHistorialDeEnvios();
        vistaDetalleDelEnvio.darClickEnVerDetalle(29);
    }
}