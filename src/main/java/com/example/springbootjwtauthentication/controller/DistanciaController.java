package com.example.springbootjwtauthentication.controller;

import com.example.springbootjwtauthentication.payload.request.DistanciaRequest;
import com.example.springbootjwtauthentication.payload.request.EasyDistanceRequest;
import com.example.springbootjwtauthentication.payload.response.bing.Resource;
import com.example.springbootjwtauthentication.service.implementations.BingMapsService;
import com.example.springbootjwtauthentication.service.implementations.GoogleMapsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/v1/api")
public class DistanciaController {

    @Autowired
    private GoogleMapsService googleMapsService;

    @Autowired
    private BingMapsService bingMapsService;

    @GetMapping("/calcularDistancia/google")
    public double calcularDistanciaGoogle(@RequestBody DistanciaRequest request) {
        return googleMapsService.calcularDistancia(request.getLatitudOrigen(), request.getLongitudOrigen(), request.getLatitudDestino(), request.getLongitudDestino());
    }

    @GetMapping("/calcularDistancia/bing")
    public Resource calcularDistanciaBing(@RequestBody DistanciaRequest request) {
        return bingMapsService.calcularDistancia(request.getLatitudOrigen(), request.getLongitudOrigen(), request.getLatitudDestino(), request.getLongitudDestino());
    }

    @GetMapping("/calcularDistancia/bing/easy")
    public Resource calcularDistanciaBing(@RequestBody EasyDistanceRequest request) {
        String datosOrigen = request.getOrigen().trim();
        String[] partesDatosOrigen = datosOrigen.split(",");
        double latitudOrigen = Double.parseDouble(partesDatosOrigen[0].trim());
        double longitudOrigen = Double.parseDouble(partesDatosOrigen[1].trim());

        String datosDestino = request.getDestino().trim();
        String[] partesDatosDestino = datosDestino.split(",");
        double latitudDestino = Double.parseDouble(partesDatosDestino[0].trim());
        double longitudDestino = Double.parseDouble(partesDatosDestino[1].trim());

        return bingMapsService.calcularDistancia(latitudOrigen, longitudOrigen, latitudDestino, longitudDestino);
    }
}

