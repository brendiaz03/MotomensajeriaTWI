package com.tallerwebi.punta_a_punta;

import com.microsoft.playwright.*;
import com.tallerwebi.punta_a_punta.vistas.VistaRegistrarUsuario;
import org.junit.jupiter.api.*;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsStringIgnoringCase;

public class VistaRegistrarUsuarioE2E {
    static Playwright playwright;
    static Browser browser;
    BrowserContext context;
    VistaRegistrarUsuario vistaRegistrarUsuario;

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
        vistaRegistrarUsuario = new VistaRegistrarUsuario(page);
    }

    @AfterEach
    void cerrarContexto() {
        context.close();
    }

    @Test
    void crearUsuarioConductorConVehiculo() {
        vistaRegistrarUsuario.darClickEnRegistrarse();
        vistaRegistrarUsuario.escribirNombre("Ayrton");
        vistaRegistrarUsuario.escribirApellido("Senna");
        vistaRegistrarUsuario.escribirDomicilio("Brasil 123");
        vistaRegistrarUsuario.escribirNumeroDeTelefono("1561639242");
        vistaRegistrarUsuario.escribirNumeroDeDni("42952902");
        vistaRegistrarUsuario.escribirNombreDeUsuario("Conductor");
        vistaRegistrarUsuario.escribirEmail("conductor@gmail.com");
        vistaRegistrarUsuario.escribirContrasenia("test");
        vistaRegistrarUsuario.escribirConfirmarContrasenia("test");
        vistaRegistrarUsuario.seleccionarTipoDeUsuario("Conductor");
        vistaRegistrarUsuario.darClickEnRegistrarUsuario();
        vistaRegistrarUsuario.seleccionarTipoDeVehiculo("AUTO");
        vistaRegistrarUsuario.seleccionarModeloDeVehiculo("MCLAREN");
        vistaRegistrarUsuario.seleccionarColorDeVehiculo("VERDE");
        vistaRegistrarUsuario.escribirPatente("SNK 139");
        vistaRegistrarUsuario.escribirPesoSoportado("50");
        vistaRegistrarUsuario.escribirDimensionDisponible("300");
        vistaRegistrarUsuario.darClickEnRegistrarVehiculo();

        String url = vistaRegistrarUsuario.obtenerURLActual();
        assertThat(url, containsStringIgnoringCase("/home"));
    }

    @Test
    void crearUsuarioCliente() {
        vistaRegistrarUsuario.darClickEnRegistrarse();
        vistaRegistrarUsuario.escribirNombre("Senku");
        vistaRegistrarUsuario.escribirApellido("Ishigami");
        vistaRegistrarUsuario.escribirDomicilio("Japon 123");
        vistaRegistrarUsuario.escribirNumeroDeTelefono("1561639243");
        vistaRegistrarUsuario.escribirNumeroDeDni("42952903");
        vistaRegistrarUsuario.escribirNombreDeUsuario("Cliente");
        vistaRegistrarUsuario.escribirEmail("cliente@gmail.com");
        vistaRegistrarUsuario.escribirContrasenia("test");
        vistaRegistrarUsuario.escribirConfirmarContrasenia("test");
        vistaRegistrarUsuario.seleccionarTipoDeUsuario("Cliente");
        vistaRegistrarUsuario.darClickEnRegistrarUsuario();


        String url = vistaRegistrarUsuario.obtenerURLActual();
        assertThat(url, containsStringIgnoringCase("/home"));
    }
}
