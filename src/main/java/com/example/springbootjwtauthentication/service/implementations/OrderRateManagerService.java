package com.example.springbootjwtauthentication.service.implementations;

import com.example.springbootjwtauthentication.model.Enum.ERate;
import com.example.springbootjwtauthentication.model.Order;
import com.example.springbootjwtauthentication.model.OrderRate;
import com.example.springbootjwtauthentication.model.Rate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderRateManagerService {
    @Autowired
    private RateService rateService;
    @Autowired
    private OrderRateService orderRateService;
    @Autowired
    private GasEstimatorService gasEstimatorService;

    public void setStandardRates(Order order) {
        Rate gasRate = rateService.getRateByName(ERate.GAS);
        gasRate.getValue();

        OrderRate orderRateByGas = new OrderRate();

        orderRateByGas.setOrder(order);
        orderRateByGas.setRate(gasRate);
        orderRateByGas.setTotal(gasEstimatorService.getGasConsumptionCost(order.getEstimatedTravelDistance(),13231));
    }
}
