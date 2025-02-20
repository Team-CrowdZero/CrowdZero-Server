package com.crowdzero.crowdzero_sever.assemblyApi.service;

import com.crowdzero.crowdzero_sever.assemblyApi.domain.Assembly;
import com.crowdzero.crowdzero_sever.assemblyApi.dto.AssemblyResponseDto;
import com.crowdzero.crowdzero_sever.assemblyApi.repository.AssemblyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AssemblyService {
    private final AssemblyRepository assemblyRepository;
    private final RestTemplate restTemplate = new RestTemplate();

    private static final String API_URL = "https://crowdzero-ml-production-fcc0.up.railway.app/get_protests";

    /**
     * 매일 자정(00:00)에 기존 데이터를 삭제하고 새로운 데이터를 저장
     */
    @Scheduled(cron = "0 0 0 * * *") // 매일 자정 실행
    @Transactional
    public void fetchAndSaveAssemblies() {
        // 외부 API 호출
        String response = restTemplate.getForObject(API_URL, String.class);
        JSONObject jsonResponse = new JSONObject(response);
        JSONArray protests = jsonResponse.getJSONArray("protests");

        List<Assembly> assemblies = new ArrayList<>();

        for (int i = 0; i < protests.length(); i++) {
            JSONObject protest = protests.getJSONObject(i);

            String date = protest.getString("집회 날짜");
            String assemblyTime = protest.getString("집회 일시");
            String assemblyPlace = protest.getString("집회 장소(행진로)");
            String jurisdiction = protest.getString("관할서");
            String district = protest.getString("행정구역(동)");

            //  "신고 인원" 값 처리 (쉼표 제거 및 숫자로 변환)
            int assemblyPopulation = 0;
            if (protest.has("신고 인원") && !protest.isNull("신고 인원")) {
                String populationStr = protest.getString("신고 인원").replace(",", ""); // 쉼표 제거
                try {
                    assemblyPopulation = Integer.parseInt(populationStr);
                } catch (NumberFormatException e) {
                    assemblyPopulation = 0; // 변환 실패 시 기본값 0 설정
                }
            }

            Assembly assembly = Assembly.builder()
                    .date(date)
                    .assemblyTime(assemblyTime)
                    .assemblyPlace(assemblyPlace)
                    .assemblyPopulation(assemblyPopulation)
                    .jurisdiction(jurisdiction)
                    .district(district)
                    .build();

            assemblies.add(assembly);
        }

        // 데이터 베이스에 저장
        assemblyRepository.deleteAll();
        assemblyRepository.saveAll(assemblies);
    }

    // 날짜에 맞게 검색
    public List<AssemblyResponseDto> getAssembliesByDate(String date) {
        return assemblyRepository.findByDate(date);
    }
}
