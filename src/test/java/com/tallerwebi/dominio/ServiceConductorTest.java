package com.tallerwebi.dominio;

import com.tallerwebi.dominio.conductor.*;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.persistence.NoResultException;

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


    @Test
    public void siYoIngresoLosDatosCorrectosSeRegistraElConductorEnLaBD() throws Exception {
        // Arrange
        Conductor nuevoConductor = new Conductor("Jose", "Perez", 42952902, "juan@example.com", "password", "juanito", "Calle Falsa 123", "1234567890", "0001002900001234567891");
        when(iRepositoryConductor.buscarDuplicados(anyString(), anyString())).thenThrow(new NoResultException());

        // Act
        Boolean resultado = null;
        try {
            resultado = iServiceConductor.verificarDatosDeRegistro(nuevoConductor);
        } catch (ConductorDuplicadoException e) {
            fail("No se esperaba una excepción de conductor duplicado");
        }

        // Assert
        assertThat(resultado, equalTo(true));
    }



}
