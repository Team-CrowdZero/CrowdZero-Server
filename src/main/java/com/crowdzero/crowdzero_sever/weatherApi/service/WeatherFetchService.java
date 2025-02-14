package com.crowdzero.crowdzero_sever.weatherApi.service;

import com.crowdzero.crowdzero_sever.domain.Place;
import com.crowdzero.crowdzero_sever.weatherApi.domain.Weather;
import com.crowdzero.crowdzero_sever.weatherApi.domain.WeatherRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class WeatherFetchService {
    private final WeatherRepository weatherRepository;

    public void saveWeatherData(Weather weatherData) {
        try {
            Long areaId = (long) weatherData.getArea().getId();
            weatherRepository.findByAreaId(areaId)
                    .ifPresentOrElse(existingWeather -> {
                        // 기존 데이터 업데이트
                        existingWeather.update(
                                weatherData.getTemp(),
                                weatherData.getSkyStts(),
                                weatherData.getPm25Index(),
                                weatherData.getPm10Index()
                        );
                        weatherRepository.save(existingWeather);
                        log.info("✅ Updated weather data for area ID: {}", areaId);

                    }, () -> {
                        // 기존 데이터가 없으면 새로 저장
                        weatherRepository.save(weatherData);
                        log.info("✅ Saved new weather data for area ID: {}", areaId);
                    });

        } catch (Exception e) {
            log.error("❌ Error saving weather data: {}", weatherData, e);
        }
    }
}
