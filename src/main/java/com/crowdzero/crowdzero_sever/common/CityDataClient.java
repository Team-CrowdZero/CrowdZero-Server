package com.crowdzero.crowdzero_sever.common;

import com.crowdzero.crowdzero_sever.domain.Place;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Component
public class CityDataClient {
    private final String apiKey;

    public CityDataClient(@Value("${api.key}") String apiKey) {
        this.apiKey = apiKey;
    }

    public String fetchCityData(String placeName) {
        try {
            String encodedPlace = URLEncoder.encode(placeName, "UTF-8");
            String apiUrl = "http://openapi.seoul.go.kr:8088/" + apiKey + "/json/citydata/1/5/" + encodedPlace;

            URL url = new URL(apiUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Content-type", "application/json");

            int responseCode = conn.getResponseCode();
            log.info("Response Code: {}", responseCode);

            BufferedReader rd;
            if (responseCode >= 200 && responseCode <= 300) {
                rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            } else {
                rd = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
            }

            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = rd.readLine()) != null) {
                sb.append(line);
            }
            rd.close();
            conn.disconnect();

            String response = sb.toString();
            log.info("Response Data for {}: {}", placeName, response);
            return response;
        } catch (Exception e) {
            log.error("Error fetching city data for {}: {}", placeName, e.getMessage());
            return null;
        }
    }

    public Map<String, String> fetchMultipleCityData(List<Place> places) {
        Map<String, String> cityDataMap = new HashMap<>();
        for (Place place : places) {
            String data = fetchCityData(place.getAreaNm());
            if (data != null) {
                cityDataMap.put(place.getAreaNm(), data);
            }
        }
        return cityDataMap;
    }
}
