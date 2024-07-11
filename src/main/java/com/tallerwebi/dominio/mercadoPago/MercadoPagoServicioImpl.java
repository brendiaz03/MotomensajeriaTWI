package com.tallerwebi.dominio.mercadoPago;

import com.mercadopago.MercadoPagoConfig;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import org.json.JSONObject;

@Service
@Transactional

    public class MercadoPagoServicioImpl implements MercadoPagoServicio {

    static {
        MercadoPagoConfig.setAccessToken("APP_USR-6516945332441912-062213-33e68d63ba4f0fdd7ffb8678d2be0fa4-1867816013");
    }

    @Override
    public String pagarViajeMp(Double precioDelViaje) throws IOException {
        // Define la API Key y Endpoint de Mercado Pago
        String apiKey = "APP_USR-6516945332441912-062213-33e68d63ba4f0fdd7ffb8678d2be0fa4-1867816013";
        URL url = new URL("https://api.mercadopago.com/checkout/preferences");

        // Configura la conexión HTTP
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("POST");
        con.setRequestProperty("Content-Type", "application/json");
        con.setRequestProperty("Authorization", "Bearer " + apiKey);
        con.setDoOutput(true);

        // Construye el JSON para enviar a Mercado Pago
        JSONObject item = new JSONObject();
        item.put("title", "Viaje");
        item.put("description", "Servicio de Motomensajería");
        item.put("category_id", "viaje");
        item.put("quantity", 1);
        item.put("currency_id", "ARS");
        item.put("unit_price", precioDelViaje);

        JSONObject jsonBody = new JSONObject();
        jsonBody.put("items", new JSONObject[]{item});

        // Envia la solicitud a Mercado Pago
        try (OutputStream os = con.getOutputStream()) {
            byte[] input = jsonBody.toString().getBytes("utf-8");
            os.write(input, 0, input.length);
        }

        // Lee la respuesta de Mercado Pago
        try (BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream(), "utf-8"))) {
            StringBuilder response = new StringBuilder();
            String responseLine;
            while ((responseLine = br.readLine()) != null) {
                response.append(responseLine.trim());
            }

            // Parsea la respuesta JSON
            JSONObject jsonResponse = new JSONObject(response.toString());

            // Obtiene el ID de preferencia de la respuesta y forma la URL de redirección
            String preferenceId = jsonResponse.getString("id");
            return "https://www.mercadopago.com.ar/checkout/v1/redirect?pref_id=" + preferenceId;
        }
    }

    @Override
    public String pagarPenalizacionMp(Double montoPenalizacion) throws IOException {
        // Define la API Key y Endpoint de Mercado Pago
        String apiKey = "APP_USR-6516945332441912-062213-33e68d63ba4f0fdd7ffb8678d2be0fa4-1867816013";

        URL url = new URL("https://api.mercadopago.com/checkout/preferences");

        // Configura la conexión HTTP
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("POST");
        con.setRequestProperty("Content-Type", "application/json");
        con.setRequestProperty("Authorization", "Bearer " + apiKey);
        con.setDoOutput(true);

        // Construye el JSON para enviar a Mercado Pago
        JSONObject item = new JSONObject();
        item.put("title", "Penalización");
        item.put("description", "Pago de multa por penalización");
        item.put("category_id", "penalizacion");
        item.put("quantity", 1);
        item.put("currency_id", "ARS");
        item.put("unit_price", montoPenalizacion);

        JSONObject jsonBody = new JSONObject();
        jsonBody.put("items", new JSONObject[]{item});


        // Envia la solicitud a Mercado Pago
        try (OutputStream os = con.getOutputStream()) {
            byte[] input = jsonBody.toString().getBytes("utf-8");
            os.write(input, 0, input.length);
        }

        // Lee la respuesta de Mercado Pago
        try (BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream(), "utf-8"))) {
            StringBuilder response = new StringBuilder();
            String responseLine;
            while ((responseLine = br.readLine()) != null) {
                response.append(responseLine.trim());
            }

            // Parsea la respuesta JSON
            JSONObject jsonResponse = new JSONObject(response.toString());

            // Obtiene el ID de preferencia de la respuesta y forma la URL de redirección
            String preferenceId = jsonResponse.getString("id");
            return "https://www.mercadopago.com.ar/checkout/v1/redirect?pref_id=" + preferenceId;
        }
    }

}

