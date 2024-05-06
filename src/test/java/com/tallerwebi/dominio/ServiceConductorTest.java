package com.tallerwebi.dominio;

import com.tallerwebi.dominio.conductor.Conductor;
import com.tallerwebi.dominio.conductor.IRepositoryConductor;
import com.tallerwebi.dominio.conductor.IServiceConductor;
import com.tallerwebi.dominio.conductor.ServiceConductorImpl;
import com.tallerwebi.infraestructura.RepositoryConductorImpl;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
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
    public void verificarDatosCorrectosDelFormularioDeConductor() throws Exception {
        Conductor nuevoConductor = new Conductor("Jose", "Perez", 12345678, "juan@example.com", "password", "juanito", "Calle Falsa 123", "1234567890", "0001002900001234567891");

        String resultado = iServiceConductor.verificarDatosDeRegistro(nuevoConductor);


        assertThat(resultado,equalTo("Datos cargados con exito"));
    }



}
