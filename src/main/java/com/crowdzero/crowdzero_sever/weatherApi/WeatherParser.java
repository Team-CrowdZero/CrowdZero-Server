package com.crowdzero.crowdzero_sever.weatherApi;

import com.crowdzero.crowdzero_sever.domain.Place;
import com.crowdzero.crowdzero_sever.weatherApi.domain.Weather;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Slf4j
@Component
public class WeatherParser {
    public Weather parse(String jsonData, Place place) {
        try {
            log.info("🔍 Parsing Weather JSON Data...");
            log.debug("📄 Raw JSON: {}", jsonData);

            // JSON 객체 생성
            JSONObject jsonObject = new JSONObject(jsonData);

            // "CITYDATA" 내부 데이터 확인
            JSONObject cityData = jsonObject.optJSONObject("CITYDATA");
            if (cityData == null) {
                log.warn("⚠️ CITYDATA not found.");
                return null;
            }
            log.info("✅ CITYDATA found.");

            // WEATHER_STTS에서 기본 날씨 정보 가져오기
            JSONArray weatherSttsArray = cityData.optJSONArray("WEATHER_STTS");
            if (weatherSttsArray == null || weatherSttsArray.isEmpty()) {
                log.warn("⚠️ WEATHER_STTS not found or empty.");
                return null;
            }

            JSONObject weatherStts = weatherSttsArray.getJSONObject(0); // 첫 번째 요소 가져오기
            log.info("✅ WEATHER_STTS found.");

            int temp = weatherStts.optInt("TEMP", -999); // 기본값 -999 설정
            String pm25Index = weatherStts.optString("PM25_INDEX", "N/A");
            String pm10Index = weatherStts.optString("PM10_INDEX", "N/A");

            // 현재 시간과 가장 가까운 SKY_STTS 찾기
            JSONArray forecastArray = weatherStts.optJSONArray("FCST24HOURS");
            String skyStts = findClosestSkyStatus(forecastArray);

// Weather 객체 생성 후 반환
            return Weather.builder()
                    .temp(temp)
                    .skyStts(skyStts)
                    .pm25Index(pm25Index)
                    .pm10Index(pm10Index)
                    .area(place)
                    .build();


        } catch (Exception e) {
            log.error("❌ Error parsing weather data: ", e);
            return null;
        }
    }

    private String findClosestSkyStatus(JSONArray forecastArray) {
        if (forecastArray == null || forecastArray.isEmpty()) {
            log.warn("⚠️ FCST24HOURS list is empty.");
            return "Unknown";
        }

        // 현재 시간 기준 FCST_DT 찾기
        String currentTime = getCurrentTimeFormatted();
        String closestSkyStatus = "Unknown";
        long minTimeDiff = Long.MAX_VALUE;

        for (int i = 0; i < forecastArray.length(); i++) {
            JSONObject forecast = forecastArray.getJSONObject(i);
            String fcstDt = forecast.optString("FCST_DT", ""); // 예: "202502152000"
            String skyStatus = forecast.optString("SKY_STTS", "Unknown");

            if (!fcstDt.isEmpty()) {
                long timeDiff = Math.abs(Long.parseLong(fcstDt) - Long.parseLong(currentTime));
                if (timeDiff < minTimeDiff) {
                    minTimeDiff = timeDiff;
                    closestSkyStatus = skyStatus;
                }
            }
        }

        log.info("🌤 Closest SKY_STTS found: {}", closestSkyStatus);
        return closestSkyStatus;
    }

    private String getCurrentTimeFormatted() {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHH00");
        return now.format(formatter);
    }
}