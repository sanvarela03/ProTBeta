package com.example.springbootjwtauthentication.service.implementations;

import com.example.springbootjwtauthentication.model.City;
import com.example.springbootjwtauthentication.model.Country;
import com.example.springbootjwtauthentication.model.State;
import com.example.springbootjwtauthentication.repository.CityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CityService {
    @Autowired
    private CityRepository repository;
    @Autowired
    private StateService stateService;
    @Autowired
    private CountryService countryService;

    public boolean existsFiltered(String country, String state, String city) {
        return repository.existsFilteredByAll(country, state, city);
    }

    public boolean existsFiltered(String country, String state) {
        return repository.existsFilteredByCountryAndState(country, state);
    }

    public boolean existsFiltered(String country) {
        return repository.existsFilteredByCountry(country);
    }

    public City getCityFiltered(String country, String state, String city) {
        return repository.findFiltered(country, state, city).orElseThrow(() -> new RuntimeException("No se encontro: " + city + ", " + state + ", " + country + " ;"));
    }

    public City createCity(String countryName, String stateName, String cityName) {
        State state = stateService.getFiltered(countryName, stateName);
        City city = new City();
        city.setName(cityName);
        city.setState(state);
        repository.save(city);
        return city;
    }

    public City createCityAndState(String countryName, String stateName, String cityName) {
        Country country = countryService.getByName(countryName);
        State state = new State();
        state.setName(stateName);
        state.setInitials(stateName.substring(0, 3).toUpperCase());
        state.setCountry(country);
        stateService.save(state);

        City city = new City();
        city.setName(cityName);
        city.setState(state);
        repository.save(city);
        return city;
    }

    public City createCityAndStateAndCountry(String countryName, String stateName, String cityName) {
        Country country = new Country();
        country.setName(countryName);
        country.setInitials(countryName.substring(0, 3).toUpperCase());
        countryService.save(country);

        State state = new State();
        state.setName(stateName);
        state.setInitials(stateName.substring(0, 3).toUpperCase());
        state.setCountry(country);
        stateService.save(state);

        City city = new City();
        city.setName(cityName);
        city.setState(state);
        repository.save(city);

        return city;
    }
}
