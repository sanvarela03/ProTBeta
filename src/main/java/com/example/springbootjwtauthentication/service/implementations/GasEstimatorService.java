package com.example.springbootjwtauthentication.service.implementations;

import com.example.springbootjwtauthentication.model.Address;
import com.example.springbootjwtauthentication.model.Enum.ERate;
import com.example.springbootjwtauthentication.payload.response.bing.Resource;
import com.example.springbootjwtauthentication.service.implementations.dto.FuelConsumptionStatistics;
import com.example.springbootjwtauthentication.service.implementations.dto.TravelCost;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@Service
@Slf4j
public class GasEstimatorService {
    @Autowired
    private RateService rateService;
    @Autowired
    private TransporterService transporterService;
    @Autowired
    private BingMapsTravelService bingMapsTravelService;
    @Autowired
    private TransporterSelectionService transporterSelectionService;


    /**
     * Metodo para calcular el costo de la gasolina para una distancia dada
     *
     * @param distance            la distancia en metros (m)
     * @param fuelConsumptionRate la tasa promedio de consumo de combustible en litros cada 100km ( L / 100 km)
     */
    public double getGasConsumptionCost(double distance, double fuelConsumptionRate) {
        double gasPrice = rateService.getRateByName(ERate.GAS).getValue(); // $ / L COP per liter
        double fuelConsumptionRate_LM = fuelConsumptionRate / 100_000; // liters per meter
        double totalGasConsumption = distance * fuelConsumptionRate_LM; // liters


        return totalGasConsumption * gasPrice;
    }

    public TravelCost getGasConsumptionCost(Address from, Address to) throws ExecutionException, InterruptedException {

        Resource resource = getRoadInfo(from, to);
        log.info("RESOURCE : {}", resource);
        double distance = resource.getTravelDistance() * 1000;
        double time = resource.getTravelDuration();

        List<Double> fuelConsumptionSample = transporterService.getAllFuelConsumptionByCityId(from.getCity().getId());


        FuelConsumptionStatistics statistics = getFuelConsumptionStats(fuelConsumptionSample);

        double averageCost = getGasConsumptionCost(distance, statistics.getAverage());
        double higherCost = getGasConsumptionCost(distance, statistics.getAverage() + statistics.getStandardDeviation());
        double lowerCost = getGasConsumptionCost(distance, statistics.getAverage() - statistics.getStandardDeviation());

        return TravelCost.builder()
                .averageCost(averageCost)
                .higherCost(higherCost)
                .lowerCost(lowerCost)
                .distance(distance)
                .time(time)
                .build();
    }

    private Resource getRoadInfo(Address from, Address to) throws InterruptedException, ExecutionException {
        CompletableFuture<Resource> future = bingMapsTravelService.getDistance(from.getLatitude(), from.getLongitude(), to.getLatitude(), to.getLongitude());
        return future.get();
    }


    public FuelConsumptionStatistics getFuelConsumptionStats(List<Double> fuelConsumptionSample) {
        int populationSize = fuelConsumptionSample.size();
        double average = getAverage(fuelConsumptionSample, populationSize);
        double sum = getDifferenceSquaredSum(fuelConsumptionSample, average);
        double standardDeviation = Math.sqrt(sum / populationSize);
        return FuelConsumptionStatistics.builder()
                .average(average)
                .standardDeviation(standardDeviation)
                .build();
    }

    private double getDifferenceSquaredSum(List<Double> fuelConsumptionSample, double populationMean) {
        double sum = 0.0;
        for (double fc : fuelConsumptionSample) {
            sum += Math.pow(fc - populationMean, 2);
        }
        return sum;
    }

    private double getAverage(List<Double> fuelConsumptionSample, int populationSize) {
        double populationMean;
        double sum = 0.0;
        for (double fc : fuelConsumptionSample) {
            sum += fc;
        }
        populationMean = sum / populationSize;
        return populationMean;
    }
}
