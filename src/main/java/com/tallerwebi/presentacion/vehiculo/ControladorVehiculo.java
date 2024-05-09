package com.tallerwebi.presentacion.vehiculo;
import com.tallerwebi.dominio.conductor.Conductor;
import com.tallerwebi.dominio.conductor.ConductorDuplicadoException;
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
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;



@Controller
public class ControladorVehiculo{

    private IImageService iImageService;

    private IServicioVehiculo iServicioVehiculo;

    @Autowired
    public ControladorVehiculo(IServicioVehiculo iServicioVehiculo, IImageService _iImageService) {

        this.iServicioVehiculo = iServicioVehiculo;

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

}
