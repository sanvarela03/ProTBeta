package com.example.springbootjwtauthentication.service.implementations;

import com.example.springbootjwtauthentication.payload.response.bing.BingMapsDirectionsResponse;
import com.example.springbootjwtauthentication.payload.response.bing.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
@Slf4j
public class BingMapsService {
    @Value("${bing.maps.apikey}")
    private String apiKey;
    private final String BING_URL = "http://dev.virtualearth.net/REST/V1/Routes";
    private final String TRAVEL_MODE = "Driving";
    public Resource calcularDistancia(double latitudOrigen, double longitudOrigen, double latitudDestino, double longitudDestino) {
        String url = String.format(
                "%s/%s?wp.0=%s,%s&wp.1=%s,%s&key=%s",
                BING_URL,
                TRAVEL_MODE,
                latitudOrigen,
                longitudOrigen,
                latitudDestino,
                longitudDestino,
                apiKey
        );

        RestTemplate restTemplate = new RestTemplate();
        BingMapsDirectionsResponse response = restTemplate.getForObject(url, BingMapsDirectionsResponse.class);

        log.info("Response: {}", response);

        // Verifica que la respuesta sea exitosa y contiene información de la ruta
        if (response != null) {
            List<Resource> resources = response.getResourceSets().get(0).getResources();
            System.out.println("Respuesta: " + resources);
            if (!resources.isEmpty()) {
                return resources.get(0);
            }
        }
        // Manejar errores o devolver un valor por defecto
        return null;
    }
}
