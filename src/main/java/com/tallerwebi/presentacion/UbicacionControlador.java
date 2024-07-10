package com.tallerwebi.presentacion;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpSession;
import javax.swing.text.View;

@RestController
public class UbicacionControlador {

    @PostMapping("/actualizar-ubicacion")
    public ResponseEntity<String> ubicacion(HttpSession session,
                                            @RequestParam("latitud") double latitud,
                                            @RequestParam("longitud") double longitud,
                                            @RequestParam("precision") double precision){
        session.setAttribute("latitud", latitud);
        session.setAttribute("longitud", longitud);
        session.setAttribute("precision", precision);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Location", "/homeConductor");
        return new ResponseEntity<>(headers, HttpStatus.FOUND);
    }
}