package com.tallerwebi.presentacion;
import com.tallerwebi.dominio.conductor.Conductor;
import com.tallerwebi.dominio.exceptions.UsuarioNoEncontradoException;
import com.tallerwebi.dominio.conductor.ConductorServicio;
import com.tallerwebi.dominio.exceptions.VehiculoDuplicadoException;
import com.tallerwebi.dominio.vehiculo.VehiculoServicio;
import com.tallerwebi.dominio.vehiculo.Vehiculo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.View;

import javax.servlet.http.HttpSession;


@Controller
public class VehiculoControlador {

    private VehiculoServicio vehiculoServicio;

    private ConductorServicio conductorServicio;

    @Autowired
    public VehiculoControlador(VehiculoServicio vehiculoServicio, ConductorServicio _conductorServicio) {
        this.vehiculoServicio = vehiculoServicio;
        this.conductorServicio = _conductorServicio;
    }

    @RequestMapping(path = "/form-vehiculo", method = RequestMethod.GET)
    public ModelAndView mostrarRegistroDelVehiculo(Vehiculo nuevoVehiculo,String mensajeError,HttpSession session) throws UsuarioNoEncontradoException {
        ModelMap model = new ModelMap();
        String viewName = "form-vehiculo";
        Boolean estaLogeado = (session != null && session.getAttribute("estaLogeado") != null) ? (Boolean) session.getAttribute("estaLogeado") : false;
        model.put("estaLogeado", estaLogeado);
        if(mensajeError==null||mensajeError.isEmpty()){
            model.put("mensajeError",null);
        }else{
            model.put("vehiculo",nuevoVehiculo);
            model.put("mensajeError",mensajeError);
        }
        Conductor buscado;
        try {
            buscado = conductorServicio.obtenerConductorPorId((Integer) session.getAttribute("IDUSUARIO"));
        }catch (UsuarioNoEncontradoException e){
            model.put("mensajeError", e.getMessage() + " Por favor, vuelva a intentarlo.");
            return new ModelAndView("redirect:/*", model);
        }
            if (buscado.getVehiculo() == null) {
                model.put("noVehiculo", true);
            } else {
                model.put("noVehiculo", false);
            }
            if ((Boolean) session.getAttribute("isEditForm")) {
                model.put("isEditForm", true);
                model.put("vehiculo", buscado.getVehiculo());
            } else {
                model.put("isEditForm", false);
                model.put("vehiculo", new Vehiculo());
            }
            return new ModelAndView(viewName, model);
    }

    @PostMapping("/registrar-vehiculo")
    public ModelAndView registrarVehiculo(@ModelAttribute("vehiculo") Vehiculo nuevoVehiculo, HttpSession session) throws UsuarioNoEncontradoException {
        try{
            Vehiculo vehiculo = vehiculoServicio.registrarVehiculo(nuevoVehiculo);
            conductorServicio.RelacionarVehiculoAConductor((Integer)session.getAttribute("IDUSUARIO"), vehiculo);
            if ((session != null && session.getAttribute("estaLogeado") != null)) {
                return new ModelAndView("redirect:/home-conductor");
            } else {
                return new ModelAndView("redirect:/home?registroExitoso=true");
            }

        }catch(VehiculoDuplicadoException | UsuarioNoEncontradoException e){
            return this.mostrarRegistroDelVehiculo(nuevoVehiculo,e.getMessage(),session);
        }
    }

    @PostMapping("/editar-vehiculo")
    public ModelAndView editarVehiculo(@ModelAttribute("vehiculo") Vehiculo nuevoVehiculo, HttpSession session) throws UsuarioNoEncontradoException {
        Conductor conductor;
        try{
            conductor =conductorServicio.obtenerConductorPorId((Integer)session.getAttribute("IDUSUARIO"));
        }catch(UsuarioNoEncontradoException e){
            ModelMap model = new ModelMap();
            model.put("mensajeError", e.getMessage() + " Por favor, vuelva a intentarlo.");
            return new ModelAndView("redirect:/*", model);
        }
            nuevoVehiculo.setId(conductor.getVehiculo().getId());
            vehiculoServicio.actualizarVehiculo(nuevoVehiculo);
            session.setAttribute("isEditForm", false);
            return new ModelAndView("redirect:/perfil");
    }

    @RequestMapping(value = "/form-vehiculo-editar", method = RequestMethod.GET)
    public String mostrarEditarVehiculo(HttpSession session) {
        session.setAttribute("isEditForm", true);
        return "redirect:/form-vehiculo";
    }

}
