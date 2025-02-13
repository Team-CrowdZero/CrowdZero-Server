package com.crowdzero.crowdzero_sever.service;

import com.crowdzero.crowdzero_sever.common.CityDataClient;
import com.crowdzero.crowdzero_sever.domain.Place;
import com.crowdzero.crowdzero_sever.populationApi.Parser.PopulationParser;
import com.crowdzero.crowdzero_sever.populationApi.domain.Population;
import com.crowdzero.crowdzero_sever.populationApi.service.PopulationFetchService;
import com.crowdzero.crowdzero_sever.repository.PlaceRepository;
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
    private final PopulationParser populationParser;
    private final PopulationFetchService populationFetchService;

    @Scheduled(fixedRate = 90000) // 90초마다 실행
    public void fetchAndStoreCityData() {
        log.info("🔄 fetchAndStoreCityData() 실행됨");

        try {
            List<Place> places = placeRepository.findAll();
            log.info("📍 총 {}개 장소 데이터 조회", places.size());

            Map<String, String> cityDataMap = cityDataClient.fetchMultipleCityData(places);

            for (Place place : places) {
                log.info("📡 Fetching data for place: {}", place.getAreaNm());

                String jsonData = cityDataMap.get(place.getAreaNm());
                log.debug("📄 Received JSON: {}", jsonData);

                if (jsonData != null) {
                    // 인구 데이터 파싱
                    List<Population> parsedPopulationData = populationParser.parse(jsonData, place);
                    log.info("📊 Parsed Population Data Count: {}", parsedPopulationData.size());

                    // 데이터 저장
                    if (!parsedPopulationData.isEmpty()) {
                        populationFetchService.savePopulationData(parsedPopulationData);
                        log.info("✅ Population data saved for: {}", place.getAreaNm());
                    } else {
                        log.warn("⚠️ No population data parsed for: {}", place.getAreaNm());
                    }
                } else {
                    log.warn("⚠️ No data received for place: {}", place.getAreaNm());
                }
            }
        } catch (Exception e) {
            log.error("❌ Error fetching or storing population data: ", e);
        }
    }
}
