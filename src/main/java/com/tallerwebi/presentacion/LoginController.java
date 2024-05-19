package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.conductor.Conductor;
import com.tallerwebi.dominio.imagen.IImageService;
import com.tallerwebi.dominio.imagen.Imagen;
import com.tallerwebi.dominio.login.DatosLoginConductor;
import com.tallerwebi.dominio.login.ILoginService;
import com.tallerwebi.dominio.viaje.Viaje;
import com.tallerwebi.dominio.viaje.ViajeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;


@Controller
@SessionAttributes("isUsuarioLogueado")
public class LoginController {

    private static ILoginService iLoginService;
    private static IImageService iImageService;
    private ViajeService viajeService;

    @Autowired
    public LoginController(ILoginService _iLoginService, IImageService _iImageService, ViajeService viajeService){
        this.iLoginService = _iLoginService;
        this.iImageService = _iImageService;
        this.viajeService = viajeService;
    }

    @RequestMapping(value = "/home", method = RequestMethod.GET)
    public ModelAndView mostrarHome(HttpSession session){
        String viewName= "home";
        ModelMap model = new ModelMap();
        Boolean isUsuarioLogueado = (Boolean) session.getAttribute("isUsuarioLogueado");
        model.put("isUsuarioLogueado", isUsuarioLogueado);
        List<Viaje> viajes = viajeService.obtenerLasSolicitudesDeViajesPendientes();
        model.put("viajes", viajes);
        Imagen logo = iImageService.getImagenByName("logo");
        model.put("logo", logo);
        Imagen user = iImageService.getImagenByName("user");
        model.put("user", user);
        Imagen auto = iImageService.getImagenByName("auto");
        model.put("auto", auto);
        Imagen fondo = iImageService.getImagenByName("fondo");
        model.put("fondo", fondo);
        Imagen botonPS = iImageService.getImagenByName("botonPS");
        model.put("botonPS", botonPS);
        return new ModelAndView(viewName,model);
    }

    @RequestMapping(path = "/login", method = RequestMethod.GET)
    public ModelAndView mostrarLogin(){
        String viewName= "login-conductor";
        ModelMap model = new ModelMap();
        model.put("datosLogin",new DatosLoginConductor());
        Imagen logo = iImageService.getImagenByName("logo");
        model.put("logo", logo);
        return new ModelAndView(viewName,model);
    }

    @RequestMapping(path="/validar-login", method = RequestMethod.POST)
    public ModelAndView validarLogin(@ModelAttribute("datosLogin") DatosLoginConductor datosLoginnConductor, HttpServletRequest request) {

        ModelMap model = new ModelMap();

            Conductor conductorBuscado = iLoginService.consultarUsuario(datosLoginnConductor.getUsuario(), datosLoginnConductor.getPassword());
            if (conductorBuscado != null) {
                request.getSession().setAttribute("NOMBRE", conductorBuscado.getNombre());
                request.getSession().setAttribute("IDUSUARIO", conductorBuscado.getId());
                request.getSession().setAttribute("APELLIDO", conductorBuscado.getApellido());
                request.getSession().setAttribute("isUsuarioLogueado", true);
                model.put("error", "Usuario o clave correcta");
                return new ModelAndView("redirect:/home", model);

            }else{
                model.put("error", "Usuario o clave incorrecta");
                request.getSession().setAttribute("isUsuarioLogueado", false);
                return new ModelAndView("redirect:/login", model);

            }
    }

    @PostMapping("/viaje/accion")
    public ModelAndView procesarAccionViaje(@RequestParam("idViaje") Integer idViaje, @RequestParam("accion") String accion, HttpSession session) {
        Integer idConductor = (Integer) session.getAttribute("IDUSUARIO");
        ModelMap modelo = new ModelMap();
        List<Viaje> viajes = new ArrayList<>();
        String mensaje = "";

        if ("aceptar".equals(accion)) {
            viajeService.actualizarViajeConElIdDelConductorQueAceptoElViaje(idViaje, idConductor);
            viajes = viajeService.obtenerLosViajesAceptadosPorElConductor(idConductor);
            mensaje = "VIAJE ACEPTADO!";
        } else {
            mensaje = "Acción no válida";
        }

        modelo.put("viajesAceptados", viajes);
        modelo.put("mensaje", mensaje);

        return new ModelAndView("viajes", modelo);
    }
}