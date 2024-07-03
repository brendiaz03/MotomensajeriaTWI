package com.tallerwebi.dominio;

import com.tallerwebi.dominio.conductor.*;
import com.tallerwebi.dominio.exceptions.UsuarioNoEncontradoException;
import com.tallerwebi.dominio.vehiculo.Vehiculo;
import com.tallerwebi.dominio.viaje.Viaje;
import com.tallerwebi.dominio.viaje.ViajeRepositorio;
import com.tallerwebi.infraestructura.config.HibernateInfraestructuraTestConfig;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import javax.persistence.NoResultException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = HibernateInfraestructuraTestConfig.class)
public class ConductorServicioTest {
    private ConductorServicio conductorServicio;
    private ConductorRepositorio conductorRepositorio;
    private ViajeRepositorio viajeRepositorio;

    @BeforeEach
    public void init() {
        this.conductorRepositorio = mock(ConductorRepositorio.class);
        this.viajeRepositorio= mock(ViajeRepositorio.class);
        this.conductorServicio = new ConductorServicioImpl(conductorRepositorio,viajeRepositorio);
    }

    @Test
    public void queSeBusqueUnConductorPorIDYSiLoEncuentraEntoncesLoDevuelveCorrectamente() throws UsuarioNoEncontradoException {
        Integer idConductor = 1;
        Conductor conductor = mock(Conductor.class);

        when(conductorRepositorio.buscarConductorPorId(idConductor)).thenReturn(conductor);
        Conductor conductorObtenido = conductorServicio.obtenerConductorPorId(idConductor);

        assertThat(conductorObtenido, equalTo(conductor));
        verify(conductorRepositorio).buscarConductorPorId(idConductor);
    }

    @Test
    public void queSeBusqueUnConductorPorIDYQueLanceUnaUsuarioNoEncontradoExceptionEnCasoDeNoEncontrarlo() {
        Integer idConductor = 1;

        when(conductorRepositorio.buscarConductorPorId(idConductor)).thenThrow(NoResultException.class);

        assertThrows(UsuarioNoEncontradoException.class, () -> {
            conductorServicio.obtenerConductorPorId(idConductor);
        });

        verify(conductorRepositorio).buscarConductorPorId(idConductor);
    }

    @Test
    public void queSeEditeUnConductorExistente() {
        Conductor conductor = mock(Conductor.class);

        conductorServicio.editarConductor(conductor);

        verify(conductorRepositorio).editarConductor(conductor);
    }

    @Test
    public void queSeRelacioneCorrectamenteUnVehiculoAUnConductorBuscadoPorId() throws UsuarioNoEncontradoException {
        Conductor conductor = mock(Conductor.class);
        Vehiculo vehiculo=mock(Vehiculo.class);
        Integer conductorId =1;

        when(conductorServicio.obtenerConductorPorId(conductorId)).thenReturn(conductor);
        Boolean resultado=this.conductorServicio.RelacionarVehiculoAConductor(conductorId,vehiculo);

        assertThat(resultado,equalTo(true));
        verify(conductor).setVehiculo(vehiculo);
        verify(conductorRepositorio).editarConductor(conductor);
    }

    @Test
    public void queSeNoSeRelacioneUnVehiculoAUnConductorSiNoSeEncuentraAlConductorBuscadoPorId() throws UsuarioNoEncontradoException {
        Vehiculo vehiculo=mock(Vehiculo.class);
        Integer idConductor =1;

        when(conductorServicio.obtenerConductorPorId(idConductor)).thenThrow(NoResultException.class);

        assertThrows(UsuarioNoEncontradoException.class, () -> {conductorServicio.RelacionarVehiculoAConductor(idConductor, vehiculo);
        });
        verify(conductorRepositorio, never()).editarConductor(any(Conductor.class));
    }
    
    @Test
    public void creoDosViajesDescartadosYUnCanceladoParaLuegoVerificarSiElConductorEstaPenalizadoEnBaseASusViajesDescartadosOCancelados() {
        Conductor conductor = mock(Conductor.class);
        List<Viaje> viajesDescartados = new ArrayList<>();
        List<Viaje> viajesCancelados = new ArrayList<>();
        Viaje viajeDescartado = mock(Viaje.class);
        Viaje viajeCancelado = mock(Viaje.class);
        viajesDescartados.add(viajeDescartado);
        viajesDescartados.add(viajeDescartado);
        viajesCancelados.add(viajeCancelado);

        when(viajeRepositorio.traerTodosLosViajesDescartadosQueAfectanPenalizacionPorConductor(conductor)).thenReturn(viajesDescartados);
        when(viajeRepositorio.traerTodosLosViajesCanceladosPorConductor(conductor)).thenReturn(viajesCancelados);

        Integer cantPenalizacion = viajesDescartados.size()+(viajesCancelados.size()*2);
        when(conductor.getCantPenalizacion()).thenReturn(cantPenalizacion);

        Boolean estaPenalizado = conductorServicio.estaPenalizado(conductor);

        assertThat(estaPenalizado, equalTo(true));
        verify(conductor).setCantPenalizacion(cantPenalizacion);
        verify(conductor).setPenalizado(true);
        verify(conductor).setHoraPenalizacion(any(LocalDateTime.class));
        verify(conductorRepositorio, times(2)).editarConductor(conductor);
        verify(viajeRepositorio, times(1)).editar(viajeCancelado);
        verify(viajeRepositorio, times(2)).editar(viajeDescartado);
    }

    @Test
    public void creoDosViajesDescartadosParaLuegoVerificarSiElConductorNoEstaPenalizadoEnBaseASusViajesDescartadosOCancelados () {
        Conductor conductor = mock(Conductor.class);
        List<Viaje> viajesDescartados = new ArrayList<>();
        List<Viaje> viajesCancelados = new ArrayList<>();
        Viaje viajeDescartado = mock(Viaje.class);
        Viaje viajeCancelado = mock(Viaje.class);
        viajesDescartados.add(viajeDescartado);
        viajesDescartados.add(viajeDescartado);

        when(viajeRepositorio.traerTodosLosViajesDescartadosQueAfectanPenalizacionPorConductor(conductor)).thenReturn(viajesDescartados);
        when(viajeRepositorio.traerTodosLosViajesCanceladosPorConductor(conductor)).thenReturn(viajesCancelados);

        Integer cantPenalizacion = viajesDescartados.size()+(viajesCancelados.size()*2);
        when(conductor.getCantPenalizacion()).thenReturn(cantPenalizacion);

        Boolean estaPenalizado = conductorServicio.estaPenalizado(conductor);

        assertThat(estaPenalizado, equalTo(false));
        verify(conductorRepositorio).editarConductor(any(Conductor.class));
        verify(viajeRepositorio, never()).editar(viajeCancelado);
        verify(viajeRepositorio, never()).editar(viajeDescartado);
    }


    @Test
    public void queSeDespenaliceExitosamenteAUnConductorPreviamentePenalizado()  {
    Conductor conductor = mock(Conductor.class);

    this.conductorServicio.despenalizarConductor(conductor);

    verify(conductor).setPenalizado(false);
    verify(conductor).setHoraPenalizacion(null);
    verify(conductor).setCantPenalizacion(0);
    verify(conductorRepositorio).editarConductor(conductor);
}

}



