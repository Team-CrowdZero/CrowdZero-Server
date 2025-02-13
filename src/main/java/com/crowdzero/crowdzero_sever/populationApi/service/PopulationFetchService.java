package com.crowdzero.crowdzero_sever.populationApi.service;

import com.crowdzero.crowdzero_sever.populationApi.domain.Population;
import com.crowdzero.crowdzero_sever.populationApi.repository.PopulationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service // ✅ Component 대신 Service 사용
@RequiredArgsConstructor
public class PopulationFetchService {
    private final PopulationRepository populationRepository;

    @Transactional
    public void savePopulationData(List<Population> populationDataList) {
        for (Population newPopulation : populationDataList) {
            try {
                // ✅ 기존 데이터 존재 여부 확인
                Optional<Population> existingPopulation = populationRepository.findByPlace(newPopulation.getPlace());

                if (existingPopulation.isPresent()) {
                    Population existingData = existingPopulation.get();

                    // ✅ 기존 데이터와 새로운 데이터가 다를 경우 업데이트
                    if (!existingData.equals(newPopulation)) {
                        existingData.update(newPopulation); // update 메서드 추가 필요
                        populationRepository.save(existingData);
                        log.info("🔄 기존 데이터 업데이트: {} - {}",
                                newPopulation.getPlace().getAreaNm(), newPopulation.getPpltnTime());
                    } else {
                        log.info("⚠️ 데이터 동일 - {}의 {} 데이터가 변경되지 않아 저장하지 않음.",
                                newPopulation.getPlace().getAreaNm(), newPopulation.getPpltnTime());
                    }
                } else {
                    // ✅ 데이터 저장
                    populationRepository.save(newPopulation);
                    log.info("✅ 새 인구 데이터 저장: {} - {}",
                            newPopulation.getPlace().getAreaNm(), newPopulation.getPpltnTime());
                }
            } catch (Exception e) {
                log.error("❌ 인구 데이터 저장 중 오류 발생: {}", newPopulation, e);
            }
        }

        log.info("총 {}개의 인구 데이터 처리 완료.", populationDataList.size());
    }
}
