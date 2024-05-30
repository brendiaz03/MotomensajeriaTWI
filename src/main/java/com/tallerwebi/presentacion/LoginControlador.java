package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.conductor.Conductor;
import com.tallerwebi.dominio.conductor.ConductorNoEncontradoException;
import com.tallerwebi.dominio.conductor.ConductorServicio;
import com.tallerwebi.dominio.imagen.ImagenServicio;
import com.tallerwebi.dominio.imagen.Imagen;
import com.tallerwebi.dominio.viaje.ViajeServicio;
import com.tallerwebi.presentacion.Datos.DatosLoginConductor;
import com.tallerwebi.dominio.login.LoginServicio;
import com.tallerwebi.presentacion.Datos.DatosViaje;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@SessionAttributes("isUsuarioLogueado")
public class LoginControlador {

    private static LoginServicio loginServicio;
    private static ImagenServicio imagenServicio;
    private final ConductorServicio conductorServicio;
    private final ViajeServicio viajeServicio;
    private Double distanciaAFiltrar;

    @Autowired
    public LoginControlador(LoginServicio _LoginServicio, ImagenServicio _imagenServicio, ConductorServicio _conductorServicio, ViajeServicio viajeServicio){
        loginServicio = _LoginServicio;
        imagenServicio = _imagenServicio;
        this.conductorServicio = _conductorServicio;
        this.viajeServicio = viajeServicio;
        this.distanciaAFiltrar = 100.0;
    }

    @RequestMapping("/")
    public ModelAndView Inicio() {
        return new ModelAndView("redirect:/home");
    }

    @RequestMapping("/home")
    public ModelAndView mostrarHome(HttpServletRequest request) throws ConductorNoEncontradoException {
        ModelMap model = new ModelMap();
        String viewName = "home";

        Double latitudActual = -34.818787; // VER
        Double longitudActual =  -58.646844; // VER

        Imagen logo = imagenServicio.getImagenByName("logo");
        Imagen user = imagenServicio.getImagenByName("user");
        Imagen auto = imagenServicio.getImagenByName("auto");
        Imagen fondo = imagenServicio.getImagenByName("fondo");
        Imagen botonPS = imagenServicio.getImagenByName("botonPS");
        Boolean isUsuarioLogueado = (Boolean) request.getSession().getAttribute("isUsuarioLogueado");
        List<DatosViaje> viajesCercanosPendientes = this.viajeServicio.filtrarViajesPorDistanciaDelConductor(latitudActual, longitudActual, distanciaAFiltrar);
        Conductor conductor;

        if(request.getSession().getAttribute("IDUSUARIO") != null){
            conductor = conductorServicio.obtenerConductorPorId((Integer) request.getSession().getAttribute("IDUSUARIO"));
        }else{
            conductor = null;
        }

        request.getSession().setAttribute("isPenalizado", this.viajeServicio.estaPenalizado(conductor));

        model.put("logo", logo);
        model.put("user", user);
        model.put("auto", auto);
        model.put("fondo", fondo);
        model.put("botonPS", botonPS);
        model.put("isUsuarioLogueado", isUsuarioLogueado);
        model.put("viajes", viajesCercanosPendientes);
        model.put("conductor", conductor);
        model.put("isPenalizado", request.getSession().getAttribute("isPenalizado"));
        return new ModelAndView(viewName, model);
    }

    @RequestMapping(path = "/login")
    public ModelAndView mostrarLogin(){
        ModelMap model = new ModelMap();
        String viewName= "login-conductor";

        Imagen logo = imagenServicio.getImagenByName("logo");

        model.put("datosLogin", new DatosLoginConductor());
        model.put("logo", logo);
        return new ModelAndView(viewName, model);
    }

    @RequestMapping(path="/validar-login", method = RequestMethod.POST)
    public ModelAndView validarLogin(@ModelAttribute("datosLogin") DatosLoginConductor datosLoginnConductor, HttpServletRequest request) {
        ModelMap model = new ModelMap();

        request.getSession().invalidate();

        //HttpSession newSession = request.getSession(true);

        Conductor conductorBuscado = loginServicio.consultarUsuario(datosLoginnConductor.getUsuario(), datosLoginnConductor.getPassword());

            if (conductorBuscado != null ) {
                request.getSession().setAttribute("NOMBRE", conductorBuscado.getNombre());
                request.getSession().setAttribute("IDUSUARIO", conductorBuscado.getId());
                request.getSession().setAttribute("APELLIDO", conductorBuscado.getApellido());
                request.getSession().setAttribute("isUsuarioLogueado", true);
                request.getSession().setAttribute("isEditForm", false);
                model.put("correcto", "Usuario o clave correcta");
                return new ModelAndView("redirect:/home", model);
            }else{
                //request.getSession().setAttribute("isUsuarioLogueado", false);
                model.put("error", "Usuario o clave incorrecta");
                return new ModelAndView("redirect:/login", model);
            }
    }

    @RequestMapping(path = "/cerrar-sesion")
    public ModelAndView cerrarSesion(HttpServletRequest request) throws ConductorNoEncontradoException {
        request.getSession().invalidate(); // Invalida la sesión, lo que equivale a cerrar sesión
        return mostrarHome(request);
    }

    @RequestMapping ("/ayuda")
    public ModelAndView mostrarVistaAyuda(HttpServletRequest request) throws ConductorNoEncontradoException {
        ModelMap model = new ModelMap();

        String viewName= "ayuda";
        Imagen logo = imagenServicio.getImagenByName("logo");
        Imagen user = imagenServicio.getImagenByName("user");
        Imagen auto = imagenServicio.getImagenByName("auto");
        Imagen fondo = imagenServicio.getImagenByName("fondo");
        Imagen botonPS = imagenServicio.getImagenByName("botonPS");
        Boolean isUsuarioLogueado = (Boolean) request.getSession().getAttribute("isUsuarioLogueado");
        Conductor conductor;

        if(request.getSession().getAttribute("IDUSUARIO") != null){
            conductor = conductorServicio.obtenerConductorPorId((Integer) request.getSession().getAttribute("IDUSUARIO"));
        }else{
            conductor = null;
        }

        model.put("logo", logo);
        model.put("user", user);
        model.put("auto", auto);
        model.put("fondo", fondo);
        model.put("botonPS", botonPS);
        model.put("isUsuarioLogueado",isUsuarioLogueado);
        model.put("conductor", conductor);
        return new ModelAndView(viewName, model);
    }

    @RequestMapping("/compania")
    public ModelAndView mostrarVistaCompania(HttpServletRequest request) throws ConductorNoEncontradoException {
        ModelMap model = new ModelMap();

        String viewName = "compania";
        Imagen logo = imagenServicio.getImagenByName("logo");
        Imagen user = imagenServicio.getImagenByName("user");
        Imagen auto = imagenServicio.getImagenByName("auto");
        Imagen fondo = imagenServicio.getImagenByName("fondo");
        Imagen botonPS = imagenServicio.getImagenByName("botonPS");
        Boolean isUsuarioLogueado = (Boolean) request.getSession().getAttribute("isUsuarioLogueado");
        Conductor conductor;

        if(request.getSession().getAttribute("IDUSUARIO") != null){
            conductor = conductorServicio.obtenerConductorPorId((Integer) request.getSession().getAttribute("IDUSUARIO"));
        }else{
            conductor = null;
        }

        model.put("logo", logo);
        model.put("user", user);
        model.put("auto", auto);
        model.put("fondo", fondo);
        model.put("botonPS", botonPS);
        model.put("isUsuarioLogueado",isUsuarioLogueado);
        model.put("conductor", conductor);
        return new ModelAndView(viewName, model);
    }

    @RequestMapping(value = "/filtrarPorDistancia", method = RequestMethod.POST)
    public ModelAndView filtrarPorDistancia(@RequestParam String distancia){
        if (distancia == null || distancia.isEmpty()) {
            return new ModelAndView("redirect:/home?error=DistanciaNoSeleccionada");
        } else {
            this.distanciaAFiltrar = Double.parseDouble(distancia);
            return new ModelAndView("redirect:/home");
        }
    }
}