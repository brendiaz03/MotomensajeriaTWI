/*package com.tallerwebi.dominio;

import com.tallerwebi.dominio.ViajeRepository;
import com.tallerwebi.dominio.ViajeService;
import com.tallerwebi.infraestructura.ViajeServiceImpl;
import org.junit.jupiter.api.BeforeEach;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.*;
import static org.springframework.test.util.AssertionErrors.fail;

public class ViajeServiceTest {

    private ViajeService viajeService;
    private ViajeRepository viajeRepository;

    @BeforeEach
    public void init() {
        this.viajeRepository = mock(ViajeRepository.class);
        this.viajeService = new ViajeServiceImpl(this.viajeRepository);
    }
/*
    @Test
    public void obtenerTodosLosViajes(){
        assertThat(this.viajeRepository.obtenerTodosLosViajesDeLaBaseDeDatos().size(), equalTo(1));
    }
}*/