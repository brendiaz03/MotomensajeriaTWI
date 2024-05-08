package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.imagen.IImageService;
import com.tallerwebi.dominio.imagen.ImageService;
import com.tallerwebi.dominio.imagen.Imagen;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.Base64;
import java.util.List;

@Controller
public class ImagenController {
    private final IImageService imageService;

    @Autowired  // CON ESTO CONECTO CONTROLADOR A SERVICIO
    public ImagenController(IImageService _imageService) {
        this.imageService=_imageService;
    }


    @GetMapping("/Getimagen")
    public ModelAndView imagen()  {
        ModelMap model=new ModelMap();
        List<Imagen> imagenesGeneral = this.imageService.obtenerImagenes();
        model.put("imagenes", imagenesGeneral);
        return new ModelAndView("mostrarImagenes",model);
    }
}
