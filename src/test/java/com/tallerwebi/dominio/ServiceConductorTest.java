package com.tallerwebi.dominio;

import com.tallerwebi.dominio.conductor.*;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.Mockito.*;


public class ServiceConductorTest {
    private IServiceConductor iServiceConductor;
    private IRepositoryConductor iRepositoryConductor;

    private SessionFactory sessionFactory;

    @BeforeEach
    public void init() {
        this.iRepositoryConductor = mock(IRepositoryConductor.class);
        this.iServiceConductor = new ServiceConductorImpl(iRepositoryConductor);
    }

//    @Test
//    public void queSePuedanListarTodosLosConductores(){
//        List<Conductor> conductores= this.iServiceConductor.get();
//        assertThat(conductores.size(),equalTo(4));
//
//    }
//
//    @Test
//    public void listarLosConductoresPorDomicilio(){ //Prueba
//
//        String domicilioFiltro="Pueyrredon 3339";
//        List<Conductor> conductoresFiltrados= this.iServiceConductor.obtenerConductoresPorDomicilio(domicilioFiltro);
//
//        assertThat(conductoresFiltrados.size(),equalTo(2));
//
//    }

    @Test
    public void queAlIngresarUnDniInvalidoLanceUnaExcepcion() throws Exception {
       Conductor nuevoConductor = new Conductor(7, "Jose", "Perez", 999999999, "juan@example.com", "password", "juanito", "Calle Falsa 123", "1234567890", "0001002900001234567891");
        try {
            iServiceConductor.verificarDatosDeRegistro(nuevoConductor);
            fail("Se esperaba una excepción");
        } catch (DniInvalidoException e) {
            assertThat(e.getMessage(), containsString("DNI Inválido"));
        } catch (Exception e) {
            fail("Se esperaba una DniInvalidoException, pero se lanzó " + e.getClass().getSimpleName());
        }
    }
    @Test
    public void verificarDatosCorrectosDelFormularioDeConductor() throws Exception {
        Conductor nuevoConductor = new Conductor(1, "Jose", "Perez", 12345678, "juan@example.com", "password", "juanito", "Calle Falsa 123", "1234567890", "0001002900001234567891");

        when(iRepositoryConductor.registrar(nuevoConductor)).thenReturn(true);
        Boolean resultado = iServiceConductor.verificarDatosDeRegistro(nuevoConductor);

        assertThat(resultado,equalTo(true));
    }



}
