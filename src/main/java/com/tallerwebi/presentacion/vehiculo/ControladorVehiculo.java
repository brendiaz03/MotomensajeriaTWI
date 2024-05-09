package com.tallerwebi.presentacion.vehiculo;
import com.tallerwebi.dominio.enums.TipoVehiculo;
import com.tallerwebi.dominio.imagen.IImageService;
import com.tallerwebi.dominio.imagen.Imagen;
import com.tallerwebi.dominio.vehiculo.IServicioVehiculo;
import com.tallerwebi.dominio.vehiculo.Vehiculo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;



@Controller
public class ControladorVehiculo{

    private IImageService iImageService;

    private IServicioVehiculo servicioVehiculo;

    @Autowired
    public ControladorVehiculo(IServicioVehiculo servicioVehiculo, IImageService _iImageService) {

        this.servicioVehiculo = servicioVehiculo;

        this.iImageService = _iImageService;

    }

    @RequestMapping(path = "/vehiculo", method = RequestMethod.GET)
    public ModelAndView mostrarRegistroDelVehiculo() {

        String viewName = "registro-vehiculo";

        ModelMap model = new ModelMap();
        model.put("message", "Bienvenido a su vehiculo");
        model.put("vehiculo", new Vehiculo());
        Imagen logo = iImageService.getImagenByName("logo");
        model.put("logo", logo);
        Imagen user = iImageService.getImagenByName("user");
        model.put("user", user);

        return new ModelAndView(viewName, model);
    }
/*
    @RequestMapping(path = "/buscar-vehiculo", method = RequestMethod.GET)
    public ModelAndView filtrarVehiculosPorTipo(TipoVehiculo tipoVehiculo){

        String viewName = "buscar-vehiculos";

        ModelMap model = new ModelMap();

        model.put("message", "Bienvenido a su vehiculo");

        List<Vehiculo> vehiculosAuto = new ArrayList<>();

        for (Vehiculo vehiculo : this.servicioVehiculo.getByTipoDeVehiculo(tipoVehiculo)){

            if(vehiculo.getTipoDeVehiculo().equals(TipoVehiculo.AUTO)) {
            vehiculosAuto.add(vehiculo);
            }
        }

        model.put("vehiculos",this.servicioVehiculo.getByTipoDeVehiculo(tipoVehiculo));

        return new ModelAndView(viewName, model);

    }

    public List<Vehiculo> obtenerTodosLosVehiculos() {

        List<Vehiculo>vehiculos = this.servicioVehiculo.obtenerTodosLosVehiculos();

        return vehiculos;
    }*/

}
