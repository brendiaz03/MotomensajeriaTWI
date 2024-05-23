package com.tallerwebi.presentacion;
import com.tallerwebi.dominio.conductor.Conductor;
import com.tallerwebi.dominio.conductor.ConductorNoEncontradoException;
import com.tallerwebi.dominio.conductor.ConductorServicio;
import com.tallerwebi.dominio.imagen.ImagenServicio;
import com.tallerwebi.dominio.imagen.Imagen;
import com.tallerwebi.dominio.vehiculo.VehiculoServicio;
import com.tallerwebi.dominio.vehiculo.Vehiculo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import javax.servlet.http.HttpSession;


@Controller
public class VehiculoControlador {

    private ImagenServicio imagenServicio;

    private VehiculoServicio vehiculoServicio;

    private ConductorServicio conductorServicio;

    @Autowired
    public VehiculoControlador(VehiculoServicio vehiculoServicio, ImagenServicio _imagenServicio, ConductorServicio _conductorServicio) {

        this.vehiculoServicio = vehiculoServicio;

        this.imagenServicio = _imagenServicio;

        this.conductorServicio = _conductorServicio;

    }

    @RequestMapping(path = "/vehiculo", method = RequestMethod.GET)
    public ModelAndView mostrarRegistroDelVehiculo(HttpSession session) throws ConductorNoEncontradoException {

        ModelMap model = new ModelMap();
        Imagen logo = imagenServicio.getImagenByName("logo");
        model.put("logo", logo);
        Imagen user = imagenServicio.getImagenByName("user");
        model.put("user", user);

        String viewName = "form-vehiculo";

        Boolean editar = (Boolean) session.getAttribute("isEditForm");
        Conductor conductor = conductorServicio.obtenerConductorPorId( (Integer) session.getAttribute("IDUSUARIO"));

        if (editar != null && editar) {
            model.put("editar", editar);
            model.put("vehiculo", conductor.getVehiculo());

            return new ModelAndView(viewName, model);
        }else {
            model.put("editar", false);
            model.put("vehiculo", new Vehiculo());

            return new ModelAndView(viewName, model);
        }
    }

    @PostMapping("/registro-vehiculo")
    public ModelAndView registrarVehiculo(@ModelAttribute("vehiculo") Vehiculo nuevoVehiculo, HttpSession session) throws ConductorNoEncontradoException {
       ModelMap model = new ModelMap();

       Conductor conductor = conductorServicio.obtenerConductorPorId((Integer)session.getAttribute("IDUSUARIO"));

       Boolean editar = (Boolean) session.getAttribute("isEditForm");

       if(editar != null && editar){
           nuevoVehiculo.setId((Long)session.getAttribute("idVehiculo"));
           vehiculoServicio.actualizarVehiculo(nuevoVehiculo);
           conductorServicio.RelacionarVehiculoAConductor(conductor.getId(), nuevoVehiculo);
           session.setAttribute("isEditForm", false);
           return new ModelAndView("redirect:/perfil");
       }else{
           Vehiculo vehiculo = vehiculoServicio.registrarVehiculo(nuevoVehiculo);
           if(vehiculo != null){
               conductorServicio.RelacionarVehiculoAConductor(conductor.getId(), vehiculo);
               return new ModelAndView("redirect:/home");
           }else{
               model.put("error", "Patente Repetida");
               return new ModelAndView("redirect:/registro-vehiculo", model);
           }
       }
    }

    @RequestMapping(value = "/editar-vehiculo", method = RequestMethod.GET)
    public String mostrarEditarVehiculo(HttpSession session) {
        session.setAttribute("isEditForm", true);
        return "redirect:/vehiculo";
    }

}
