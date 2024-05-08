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

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;


@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = HibernateInfraestructuraTestConfig.class)

@Repository
public class RepositoryConductorTest {

    @Autowired
    private SessionFactory sessionFactory;

   private IRepositoryConductor iRepositoryConductor;

   private List<Conductor> conductores;


    @BeforeEach //INICIAR LA BD CARGADA
    public void init(){

       this.iRepositoryConductor= new RepositoryConductorImpl(this.sessionFactory);
////      Instanciamos conductores en el constructor
//        Conductor nuevoConductor= new Conductor("Piccolo","Daimaku",42952902,"piccolo.daimaku@gmail.com","pico123","Namekian","Pueyrredon 3339","1161639242","1234567890123456789012");
//        Conductor nuevoConductor2= new Conductor("Goku","Son",42952903,"goku.son@gmail.com","goku123","Saiyan1","Pueyrredon 3339","1161639242","1234567890123456789013");
//        Conductor nuevoConductor3= new Conductor("Gohan","Son",42952904,"gohan.so@gmail.com","gohan123","Saiyan2","Pueyrredon 3340","1161639242","1234567890123456789014");
//        Conductor nuevoConductor4= new Conductor("Goten","Son",42952905,"goten.so@gmail.com","goten123","Saiyan3","Pueyrredon 3340","1161639242","1234567890123456789015");
//        this.conductores = new ArrayList<>();
//        conductores.add(nuevoConductor);
//        conductores.add(nuevoConductor2);
//        conductores.add(nuevoConductor3);
//        conductores.add(nuevoConductor4);
   }

    @Test
    @Transactional
    @Rollback
    public void queSePuedaRegistrarUnConductor(){
        Conductor nuevoConductor= new Conductor(1, "Piccolo", "Daimaku", 42952910, "piccolo.daimaku@gmail.com", "pico123", "Namekian", "Pueyrredon 3339", "1161639242", "1234567890123456789012");
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
       Conductor nuevoConductor= new Conductor(13, "Goku", "Son", 42952910, "goku.son@gmail.com", "goku1235", "Gokuwu", "Pueyrredon 3340", "1161639243", "1234567891234567891234");
       this.sessionFactory.getCurrentSession().save(nuevoConductor);
       System.out.println(nuevoConductor.getId());
        System.out.println("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA");

        Conductor conductorBuscado = this.iRepositoryConductor.buscarConductor(13);
       //hay un problema, el conductor se agrega a la DB pero no se muestra.
       assertThat(conductorBuscado.getNumeroDeDni(),equalTo(42952910));
    }
//
//    @Test
//    @Transactional
//    @Rollback
//    public void queSePuedaActualizarDatosDelConductor(){
//
//        Conductor nuevoConductor= new Conductor(1, "Piccolo", "Daimaku", 42952902, "piccolo.daimaku@gmail.com", "pico123", "Namekian", "Pueyrredon 3339", "1161639242", "1234567890123456789012");
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