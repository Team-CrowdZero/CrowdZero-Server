package com.crowdzero.crowdzero_sever.populationApi.Parser;

import com.crowdzero.crowdzero_sever.domain.Place;
import com.crowdzero.crowdzero_sever.populationApi.domain.Population;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Component
public class PopulationParser {
    public List<Population> parse(String jsonData, Place place) {
        List<Population> populationList = new ArrayList<>();

        try {
            log.info("🔍 Parsing JSON Data...");
            log.debug("📄 Raw JSON: {}", jsonData);

            JSONObject jsonObject = new JSONObject(jsonData);
            JSONObject cityData = jsonObject.optJSONObject("CITYDATA");

            if (cityData == null) {
                log.warn("⚠️ CITYDATA not found in JSON.");
                return populationList;
            }
            log.info("✅ CITYDATA found.");

            JSONArray populationArray = cityData.optJSONArray("LIVE_PPLTN_STTS");
            if (populationArray == null || populationArray.isEmpty()) {
                log.warn("⚠️ AREA_CONGEST_LVL not found or empty.");
                return populationList;
            }
            log.info("✅ AREA_CONGEST_LVL found with {} records.", populationArray.length());

            for (int i = 0; i < populationArray.length(); i++) {
                JSONObject populationJson = populationArray.getJSONObject(i);

                log.debug("Parsing population data: {}", populationJson.toString());

                Population population = Population.builder()
                        .areaCongestLvl(populationJson.optString("AREA_CONGEST_LVL"))
                        .areaCongestMsg(populationJson.optString("AREA_CONGEST_MSG"))
                        .areaPpltnMax(parseIntegerSafe(populationJson, "AREA_PPLTN_MAX")) // Integer 변환 적용
                        .areaPpltnMin(parseIntegerSafe(populationJson, "AREA_PPLTN_MIN")) // Integer 변환 적용
                        .ppltnTime(populationJson.optString("PPLTN_TIME"))
                        .place(place)
                        .build();

                populationList.add(population);
            }

            log.info("✅ Successfully parsed {} population records.", populationList.size());
        } catch (Exception e) {
            log.error("❌ Error parsing population data: ", e);
        }

        return populationList;
    }

    private Integer parseIntegerSafe(JSONObject jsonObject, String key) {
        try {
            if (jsonObject.has(key)) {
                Object value = jsonObject.get(key);
                if (value instanceof Number) {
                    return ((Number) value).intValue();
                } else if (value instanceof String && !((String) value).isEmpty()) {
                    return Integer.parseInt((String) value);
                }
            }
        } catch (Exception e) {
            log.warn("Integer 변환 실패: key={}, value={}", key, jsonObject.optString(key));
        }
        return null;
    }
}
