package com.crowdzero.crowdzero_sever.accidentApi;

import com.crowdzero.crowdzero_sever.accidentApi.domain.Accident;
import com.crowdzero.crowdzero_sever.domain.Place;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Component
public class AccidentParser {
    public List<Accident> parse(String jsonData, Place place) {
        List<Accident> accidentList = new ArrayList<>();

        try {
            log.info("🔍 Parsing JSON Data...");
            log.debug("📄 Raw JSON: {}", jsonData); // 원본 JSON 데이터 확인

            // 전체 JSON 객체 생성
            JSONObject jsonObject = new JSONObject(jsonData);

            // "CITYDATA" 내부 데이터 확인
            JSONObject cityData = jsonObject.optJSONObject("CITYDATA");
            if (cityData == null) {
                log.warn("⚠️ CITYDATA not found in JSON.");
                return accidentList; // 데이터가 없으면 빈 리스트 반환
            }
            log.info("✅ CITYDATA found.");

            // "ACDNT_CNTRL_STTS"가 최상위에 존재하는지 확인
            JSONArray accidentArray = cityData.optJSONArray("ACDNT_CNTRL_STTS");
            if (accidentArray == null || accidentArray.isEmpty()) {
                log.warn("⚠️ ACDNT_CNTRL_STTS not found or empty.");
                return accidentList;
            }
            log.info("✅ ACDNT_CNTRL_STTS found with {} records.", accidentArray.length());

            // 사고 데이터 리스트 파싱
            for (int i = 0; i < accidentArray.length(); i++) {
                JSONObject accidentJson = accidentArray.getJSONObject(i);

                // 로그 추가: JSON에서 어떤 데이터를 읽고 있는지 확인
                log.debug("Parsing accident data: {}", accidentJson.toString());

                Accident accident = Accident.builder()
                        .acdntOccrDt(accidentJson.optString("ACDNT_OCCR_DT"))
                        .expClrDt(accidentJson.optString("EXP_CLR_DT"))
                        .acdntInfo(accidentJson.optString("ACDNT_INFO"))
                        .acdntX(parseDoubleSafe(accidentJson, "ACDNT_X"))
                        .acdntY(parseDoubleSafe(accidentJson, "ACDNT_Y"))
                        .acdntTime(accidentJson.optString("ACDNT_TIME"))
                        .area(place)
                        .build();

                accidentList.add(accident);
            }

            log.info("✅ Successfully parsed {} accident records.", accidentList.size());
        } catch (Exception e) {
            log.error("❌ Error parsing accident data: ", e);
        }

        return accidentList;
    }

    // 문자열 또는 숫자를 안전하게 Double로 변환하는 메서드
    private Double parseDoubleSafe(JSONObject jsonObject, String key) {
        try {
            if (jsonObject.has(key)) {
                Object value = jsonObject.get(key);
                if (value instanceof Number) {
                    return ((Number) value).doubleValue();
                } else if (value instanceof String) {
                    return Double.parseDouble((String) value);
                }
            }
        } catch (Exception e) {
            log.warn("Double 변환 실패: key={}, value={}", key, jsonObject.optString(key));
        }
        return null;
    }
}