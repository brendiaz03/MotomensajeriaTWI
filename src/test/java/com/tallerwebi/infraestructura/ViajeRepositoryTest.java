package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.Viaje;
import com.tallerwebi.dominio.ViajeRepository;
import com.tallerwebi.infraestructura.config.HibernateInfraestructuraTestConfig;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.equalToIgnoringCase;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {HibernateInfraestructuraTestConfig.class})
public class ViajeRepositoryTest {

    @Autowired
    private SessionFactory sessionFactory;
    private ViajeRepository viajeRepository;

    @BeforeEach
    public void init() {
        this.viajeRepository = new ViajeRepositoryImpl(this.sessionFactory);
    }

    @Test
    @Transactional
    @Rollback
    public void queSePuedaGuardarViajeUnViajeEnLaBaseDeDatos(){
        // Preparación
        Viaje viaje = dadoQueSeGuardaUnViajeEnLaBaseDeDatos();

        // Ejecución
        Viaje viajeActual = dadoQueObtengoUnViajeGuardadoPorIdPaquete(viaje);

        // Validación
        assertThat(viajeActual.getIdPaquete(), equalTo(viaje.getIdPaquete()));
    }

    @Test
    @Transactional
    @Rollback
    public void queSeObtenganTodosLosViajesDeLaBaseDeDatos(){
        // Ejecución
        // Viajes ya insertados manualmente en la Base De Datos

        // Ejecución
        Integer viajesObtenidos = this.viajeRepository.obtenerTodosLosViajesDeLaBaseDeDatos().size();

        // Validación
        assertThat(viajesObtenidos, equalTo(6));
    }

    @Test
    @Transactional
    public void queSePuedanObtenerTodosLosViajesDeUnCliente(){
        // Ejecución
        // Viajes ya insertados manualmente en la Base De Datos
        Integer idClienteABuscar = 1;

        List<Viaje> viajes = dadoQueObtengoLosViajesDelClientePorSuId(idClienteABuscar);

        validarLosDatosDelViaje(viajes);
    }

    private static void validarLosDatosDelViaje(List<Viaje> viajes) {
        assertThat(viajes.size(), equalTo(3));
        assertThat(viajes.get(0).getLugarDeSalida(), equalToIgnoringCase("Miami"));
        assertThat(viajes.get(0).getLugarDeLlegada(), equalToIgnoringCase("New York"));
        assertThat(viajes.get(1).getLugarDeSalida(), equalToIgnoringCase("Argentina"));
        assertThat(viajes.get(1).getLugarDeLlegada(), equalToIgnoringCase("Brasil"));
        assertThat(viajes.get(2).getLugarDeSalida(), equalToIgnoringCase("Buenos Aires"));
        assertThat(viajes.get(2).getLugarDeLlegada(), equalToIgnoringCase("Cordoba"));
    }

    private List<Viaje> dadoQueObtengoLosViajesDelClientePorSuId(Integer idClienteABuscar) {
        List<Viaje> viajes = this.viajeRepository.obtenerLosViajesDeUnCliente(idClienteABuscar);
        return viajes;
    }

    private Viaje dadoQueObtengoUnViajeGuardadoPorIdPaquete(Viaje viaje) {
        Viaje viajeActual = this.viajeRepository.ObtenerViajePorIdPaquete(viaje.getIdPaquete());
        return viajeActual;
    }

    private Viaje dadoQueSeGuardaUnViajeEnLaBaseDeDatos() {
        Viaje viaje = new Viaje(1,1,1, "Miami", "New York");
        this.viajeRepository.guardarViaje(viaje);
        return viaje;
    }
}
