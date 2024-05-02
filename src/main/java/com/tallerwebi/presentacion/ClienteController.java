package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.ClienteService;
import com.tallerwebi.dominio.Paquete;
import com.tallerwebi.dominio.dto.DatosPaquete;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;

@RestController
@Transactional
public class ClienteController {

    private ClienteService clienteService;

    @Autowired
    public ClienteController(ClienteService clienteService) {
        this.clienteService = clienteService;
    }


    //Stand By (Ver si normalizar con Latitud, Direccion, etc o hacerlo tranqui)
    @RequestMapping(path = "/armar-paquete", method = RequestMethod.GET)
    public ModelAndView mostrarFormArmarPaquete (){
        ModelMap modelo = new ModelMap();
        modelo.put("datosPaquete", new DatosPaquete());
        return new ModelAndView("form-armar-paquete", modelo);
    }

    @RequestMapping(path = "/validar-paquete", method = RequestMethod.POST)
    public ModelAndView validarPaquete (@ModelAttribute ("datosPaquete") DatosPaquete datosPaquete, HttpServletRequest request){
        ModelMap modelo = new ModelMap();
        Paquete paqueteActual = new Paquete(datosPaquete.getDomicilioSalida(), datosPaquete.getDomicilioEntrega(), datosPaquete.getCodigoPostal(), datosPaquete.getEsFragil());
        Double valorDelEnvio = this.clienteService.calcularEnvio(paqueteActual.getDomicilioSalida(), paqueteActual.getDomicilioEntrega());
        Integer idCliente = (Integer) request.getSession().getAttribute("IDCLIENTE");
        paqueteActual.setValorDelEnvio(valorDelEnvio);
        paqueteActual.setIdCliente(idCliente);
        modelo.put("paquete", paqueteActual);
        this.clienteService.guardarPaquete(paqueteActual);
        return new ModelAndView("ver-paquete", modelo);
    }


    @RequestMapping(path = "/buscar-paquete", method = RequestMethod.GET)
    public ModelAndView formBuscarPaquete() {
        ModelMap modelo = new ModelMap();
        modelo.put("paquete", new Paquete());
        return new ModelAndView("form-buscar-paquete", modelo);
    }


    //Se puede hacer con @RequestParam o DatosPaquete.
    @RequestMapping(path = "/ver-paquete", method = RequestMethod.POST)
    public ModelAndView verPaquete(@RequestParam ("idPaquete") Integer idPaquete){
        Paquete paqueteActual = this.clienteService.buscarPaquete(idPaquete);
        ModelMap modelo = new ModelMap();
        modelo.put("paquete", paqueteActual);
        return new ModelAndView("ver-paquete", modelo);
    }
}