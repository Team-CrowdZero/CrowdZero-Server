package com.crowdzero.crowdzero_sever.weatherApi.service;

import com.crowdzero.crowdzero_sever.accidentApi.domain.AccidentRepository;
import com.crowdzero.crowdzero_sever.weatherApi.domain.WeatherRepository;
import com.crowdzero.crowdzero_sever.weatherApi.web.dto.WeatherResponseDto;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class WeatherService {
    private final WeatherRepository weatherRepository;

    public WeatherService(WeatherRepository weatherRepository) {
        this.weatherRepository = weatherRepository;
    }

    public WeatherResponseDto getWeatherByAreaId(Long areaId) {
        return weatherRepository.findByAreaId(areaId)
                .map(WeatherResponseDto::new)
                .orElse(null);
    }
}
