package com.crowdzero.crowdzero_sever.service;

import com.crowdzero.crowdzero_sever.accidentApi.AccidentParser;
import com.crowdzero.crowdzero_sever.accidentApi.domain.Accident;
import com.crowdzero.crowdzero_sever.accidentApi.service.AccidentFetchService;
import com.crowdzero.crowdzero_sever.common.CityDataClient;
import com.crowdzero.crowdzero_sever.domain.Place;
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
    private final AccidentParser accidentParser;
    private final AccidentFetchService accidentFetchService;// 예제 지역명 (실제 사용 시 동적으로 설정 가능)

    @Scheduled(fixedRate = 90000) // 2분마다 실행 TODO: 정시나 30분마다 실행되도록 수정 요망
    public void fetchAndStoreCityData() {
        // **전체적인 구조**
        // 1) 아래 반복문에서 CityDataClient를 활용해 장소별 공공 API 데이터(전체 내용)를 가져옴
        // 2) API에 해당하는 파서로 파싱
        // 3) 저장하는 fetchService를 실행해 정보 저장

        try {
            List<Place> places = placeRepository.findAll();
            Map<String, String> cityDataMap = cityDataClient.fetchMultipleCityData(places);

            for (Place place : places) {
                String jsonData = cityDataMap.get(place.getAreaNm());
                if (jsonData != null) {
                    List<Accident> parsedAccidentData = accidentParser.parse(jsonData, place); // 도로통제 파싱 명렁어
                    // TODO: 인구 파싱 명령어
                    // TODO: 날씨 파싱 명령어

                    accidentFetchService.saveAccidentData(parsedAccidentData); // 도로통제 저장 명렁어
                    // TODO: 인구 저장 명렁어
                    // TODO: 날씨 저장 명령어

                    log.info("Successfully saved accident data for: {}", place.getAreaNm());
                }
            }
        } catch (Exception e) {
            log.error("Error fetching or storing accident data: ", e);
        }
    }
}