package com.crowdzero.crowdzero_sever.service;

import com.crowdzero.crowdzero_sever.accidentApi.AccidentParser;
import com.crowdzero.crowdzero_sever.accidentApi.domain.Accident;
import com.crowdzero.crowdzero_sever.accidentApi.service.AccidentFetchService;
import com.crowdzero.crowdzero_sever.common.CityDataClient;
import com.crowdzero.crowdzero_sever.domain.Place;
import com.crowdzero.crowdzero_sever.populationApi.Parser.PopulationParser;
import com.crowdzero.crowdzero_sever.populationApi.domain.Population;
import com.crowdzero.crowdzero_sever.populationApi.service.PopulationFetchService;
import com.crowdzero.crowdzero_sever.repository.PlaceRepository;
import com.crowdzero.crowdzero_sever.weatherApi.WeatherParser;
import com.crowdzero.crowdzero_sever.weatherApi.domain.Weather;
import com.crowdzero.crowdzero_sever.weatherApi.service.WeatherFetchService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Slf4j
@Component
@RequiredArgsConstructor
public class CityDataScheduler {
    private final PlaceRepository placeRepository;
    private final CityDataClient cityDataClient;

    private final AccidentParser accidentParser;
    private final AccidentFetchService accidentFetchService;

    private final PopulationParser populationParser;
    private final PopulationFetchService populationFetchService;

    private final WeatherParser weatherParser;
    private final WeatherFetchService weatherFetchService;

    @Scheduled(fixedRate = 30 * 60 * 1000) // 30분마다 실행
    public void fetchAndStoreCityData() {
        try {
            List<Place> places = placeRepository.findAll();
            Map<String, String> cityDataMap = cityDataClient.fetchMultipleCityData(places);

            for (Place place : places) {
                String jsonData = cityDataMap.get(place.getAreaNm());
                if (jsonData != null) {
                    List<Accident> parsedAccidentData = accidentParser.parse(jsonData, place); // 도로통제 파싱 명렁어
                    List<Population> parsedPopulationData = populationParser.parse(jsonData, place);
                    Weather parsedWeatherData = weatherParser.parse(jsonData,place); // TODO: 날씨 파싱 명령어

                    accidentFetchService.saveAccidentData(parsedAccidentData); // 도로통제 저장 명렁어
                    populationFetchService.savePopulationData(parsedPopulationData);
                    weatherFetchService.saveWeatherData(parsedWeatherData);// TODO: 날씨 저장 명령어

                    log.info("Successfully saved data for: {}", place.getAreaNm());
                }
            }
        } catch (Exception e) {
            log.error("Error fetching or storing data: ", e);
        }
    }
}
