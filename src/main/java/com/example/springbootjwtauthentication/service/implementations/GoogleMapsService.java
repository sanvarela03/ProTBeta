package com.example.springbootjwtauthentication.service.implementations;

import com.example.springbootjwtauthentication.payload.response.google.GoogleMapsDirectionsResponse;
import com.example.springbootjwtauthentication.payload.response.google.Route;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@Slf4j
public class GoogleMapsService {

    @Value("${google.maps.apikey}")
    private String apiKey;

    private final String DIRECTIONS_API_URL = "https://maps.googleapis.com/maps/api/directions/json";

    public double calcularDistancia(double latitudOrigen, double longitudOrigen, double latitudDestino, double longitudDestino) {
        String url = String.format("%s?origin=%s,%s&destination=%s,%s&key=%s",
                DIRECTIONS_API_URL, latitudOrigen, longitudOrigen, latitudDestino, longitudDestino, apiKey);

        RestTemplate restTemplate = new RestTemplate();
        GoogleMapsDirectionsResponse response = restTemplate.getForObject(url, GoogleMapsDirectionsResponse.class);

        log.info("ESTADO DE LA RESPUESTA: {}", response.getStatus());

        // Verifica que la respuesta sea exitosa y contiene información de la ruta
        if (response != null && "OK".equals(response.getStatus())) {

            Route route = response.getRoutes().get(0);
            return response.getRoutes().get(0).getLegs().get(0).getDistance().getValue();
        } else {
            // Manejar errores o devolver un valor por defecto
            return -1.0;
        }
    }
}
