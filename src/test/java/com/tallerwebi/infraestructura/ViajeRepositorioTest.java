package com.tallerwebi.infraestructura;

import com.tallerwebi.infraestructura.config.HibernateInfraestructuraTestConfig;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;


@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = HibernateInfraestructuraTestConfig.class)
public class ViajeRepositorioTest {

}
