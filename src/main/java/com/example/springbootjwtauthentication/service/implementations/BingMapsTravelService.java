package com.example.springbootjwtauthentication.service.implementations;


import com.example.springbootjwtauthentication.model.Address;
import com.example.springbootjwtauthentication.model.Order;
import com.example.springbootjwtauthentication.payload.response.bing.BingMapsDirectionsResponse;
import com.example.springbootjwtauthentication.payload.response.bing.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@Service
@Slf4j
public class BingMapsTravelService {
    @Value("${bing.maps.apikey}")
    private String apiKey;
    private final String BING_URL = "http://dev.virtualearth.net/REST/V1/Routes";
    private final String TRAVEL_MODE = "Driving";

    @Autowired
    private OrderService orderService;


    public CompletableFuture<Resource> getDistance(double fromLatitude, double fromLongitude, double toLatitude, double toLongitude) throws InterruptedException {
        String url = getFormat(fromLatitude, fromLongitude, toLatitude, toLongitude);
        RestTemplate restTemplate = new RestTemplate();
        BingMapsDirectionsResponse response = restTemplate.getForObject(url, BingMapsDirectionsResponse.class);

        List<Resource> resources = response.getResourceSets().get(0).getResources();

        // Artificial delay of 1s for demonstration purposes
        Thread.sleep(1000L);

        return CompletableFuture.completedFuture(resources.get(0));
    }

//    @Async
//    public void findEstimatedDistance(Order order) {
//        double fromLatitude = order.getPickupAddress().getLatitude();
//        double fromLongitude = order.getPickupAddress().getLongitude();
//        double toLatitude = order.getDeliveryAddress().getLatitude();
//        double toLongitude = order.getDeliveryAddress().getLongitude();
//
//
//        String url = getFormat(fromLatitude, fromLongitude, toLatitude, toLongitude);
//
//        log.info("URL: {}", url);
//
//        RestTemplate restTemplate = new RestTemplate();
//        BingMapsDirectionsResponse response = restTemplate.getForObject(url, BingMapsDirectionsResponse.class);
//
//        List<Resource> resources = response.getResourceSets().get(0).getResources();
//
//        Resource r = resources.get(0);
//
//        order.setEstimatedTravelDistance(r.getTravelDistance() * 1000);
//        order.setEstimatedTravelDuration(r.getTravelDuration());
//
//
//        log.info("TAREA REALIZADA EN EL SUBPROCESO: {}", Thread.currentThread().getName());
//
//        orderService.saveOrder(order);
//    }

    public Resource getTravelInformation(Address from, Address to) {
        double fromLatitude = from.getLatitude();
        double fromLongitude = from.getLongitude();
        double toLatitude = to.getLatitude();
        double toLongitude = to.getLongitude();

        String url = getFormat(fromLatitude, fromLongitude, toLatitude, toLongitude);

        log.info("URL: {}", url);

        RestTemplate restTemplate = new RestTemplate();
        BingMapsDirectionsResponse response = restTemplate.getForObject(url, BingMapsDirectionsResponse.class);

        List<Resource> resources = response.getResourceSets().get(0).getResources();

        Resource r = resources.get(0);
        return r;
    }

    private String getFormat(double fromLatitude, double fromLongitude, double toLatitude, double toLongitude) {
        return String.format(
                "%s/%s?wp.0=%s,%s&wp.1=%s,%s&key=%s",
                BING_URL,
                TRAVEL_MODE,
                fromLatitude,
                fromLongitude,
                toLatitude,
                toLongitude,
                apiKey
        );
    }
}
