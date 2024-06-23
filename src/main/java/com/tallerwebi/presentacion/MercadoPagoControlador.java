package com.tallerwebi.presentacion;

import com.mercadopago.client.preference.PreferenceBackUrlsRequest;
import com.mercadopago.client.preference.PreferenceRequest;

import com.tallerwebi.dominio.mercadoPago.MercadoPagoServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class MercadoPagoControlador{

    private final MercadoPagoServicio mercadoPagoServicio;

    @Autowired
    public MercadoPagoControlador(MercadoPagoServicio mercadoPagoServicio) {
        this.mercadoPagoServicio = mercadoPagoServicio;
    }

    @RequestMapping(path = "/preferencia", method = RequestMethod.POST)
    public ResponseEntity<String> generarPreferencia(@RequestParam("precioTotal") Double precioTotal) {
        try {
            String  idPreferencia =  this.mercadoPagoServicio.pagarViajeMp(precioTotal);
            PreferenceBackUrlsRequest backUrls = PreferenceBackUrlsRequest.builder()
                    .success("http://localhost:8080/home")
                    .failure("http://localhost:8080/home").build();
            PreferenceRequest prefRequest = PreferenceRequest.builder().autoReturn("approved")
                    .backUrls(backUrls).build();

            return new ResponseEntity<>(idPreferencia, HttpStatus.OK);
        }catch(Exception e) {
            return new ResponseEntity<>("", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
