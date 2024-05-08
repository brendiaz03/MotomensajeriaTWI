package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.conductor.Conductor;
import com.tallerwebi.dominio.conductor.IRepositoryConductor;
import com.tallerwebi.infraestructura.config.HibernateInfraestructuraTestConfig;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import javax.transaction.Transactional;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = HibernateInfraestructuraTestConfig.class)

@Repository
public class RepositoryConductorTest {

    @Autowired
    private SessionFactory sessionFactory;

   private IRepositoryConductor iRepositoryConductor;

    @BeforeEach
    public void init(){
       this.iRepositoryConductor= new RepositoryConductorImpl(this.sessionFactory);
    }

    @Test
    @Transactional
    @Rollback
    public void queSePuedaRegistrarUnConductor(){
        Conductor nuevoConductor= new Conductor("Piccolo", "Daimaku", 42952910, "piccolo.daimaku@gmail.com", "pico123", "Namekian", "Pueyrredon 3339", "1161639242", "1234567890123456789012");
        Boolean resultado= this.iRepositoryConductor.registrar(nuevoConductor);
//        Conductor conductorObtenido= (Conductor) this.sessionFactory.getCurrentSession().createQuery("FROM Conductor WHERE numeroDeDni=42952910").getSingleResult();
//        System.out.println(conductorObtenido.getEmail());
//        assertThat(conductorObtenido,equalTo(nuevoConductor));
        assertThat(resultado,equalTo(true));
}
    @Test
    @Transactional
    @Rollback // Se encarga de dejar t0do como estaba antes de ejecutar
    public void queSePuedaBuscarUnConductorPorID (){
       Conductor nuevoConductor= new Conductor("Facundo", "Varela", 42952902, "facundo@gmail.com", "test1234", "Facuu", "asd 123", "1561639242", "1234567891234567891234");
       this.sessionFactory.getCurrentSession().save(nuevoConductor);
       System.out.println(nuevoConductor.getId());

        Conductor conductorBuscado = this.iRepositoryConductor.buscarConductor(15);
       assertThat(conductorBuscado.getNumeroDeDni(),equalTo(42952902));
    }

//    @Test
//    @Transactional
//    @Rollback // Se encarga de dejar t0do como estaba antes de ejecutar
//    public void queNoHayaConductoresDuplicados (){
//
//        Conductor nuevoConductor= new Conductor(15, "Facundo", "Varela", 42952902, "facundo@gmail.com", "test1234", "Facuu", "asd 123", "1561639242", "1234567891234567891234");
//        this.sessionFactory.getCurrentSession().save(nuevoConductor);
//        System.out.println(nuevoConductor.getId());
//
//        Conductor conductorBuscado = this.iRepositoryConductor.buscarConductor(15);
//        assertThat(conductorBuscado.getNumeroDeDni(),equalTo(42952902));
//    }

//    @Test
//    @Transactional
//    @Rollback
//    public void queSePuedaActualizarDatosDelConductor(){
//
//        Conductor nuevoConductor= new Conductor("Piccolo", "Daimaku", 42952902, "piccolo.daimaku@gmail.com", "pico123", "Namekian", "Pueyrredon 3339", "1161639242", "1234567890123456789012");
//        this.sessionFactory.getCurrentSession().save(nuevoConductor);
//
//        //Guarda al conductor con Domicilio = Pueyrredon 3339
//
//        String domicilioEsperado="Pueyrredon 3341";
//        nuevoConductor.setDomicilio(domicilioEsperado);
//        //Guarda al conductor con Domicilio = Pueyrredon 3341
//
//        Conductor conductorActualizado= this.iRepositoryConductor.actualizarConductor(nuevoConductor);
//
//        assertThat(conductorActualizado.getDomicilio(),equalTo(domicilioEsperado));
//
//    }
}
