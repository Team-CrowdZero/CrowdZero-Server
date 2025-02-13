package com.crowdzero.crowdzero_sever.accidentApi.service;

import com.crowdzero.crowdzero_sever.accidentApi.domain.Accident;
import com.crowdzero.crowdzero_sever.accidentApi.domain.AccidentRepository;
import com.crowdzero.crowdzero_sever.domain.Place;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class AccidentFetchService {
    private final AccidentRepository accidentRepository;

    public void saveAccidentData(List<Accident> accidentDataList) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"); // 날짜 포맷 맞추기
        String currentTime = LocalDateTime.now().format(formatter);

        for (Accident newAccident : accidentDataList) {
            try {
                Optional<Accident> existingAccident = accidentRepository.findExistingAccident(
                        newAccident.getAcdntX(), newAccident.getAcdntY(),
                        newAccident.getArea().getId(), newAccident.getAcdntOccrDt()
                );

                if (existingAccident.isPresent()) {
                    Accident accidentToUpdate = existingAccident.get();
                    accidentToUpdate.update(newAccident.getExpClrDt(),
                            newAccident.getAcdntInfo(),
                            newAccident.getAcdntTime());
                    accidentRepository.save(accidentToUpdate);
                } else {
                    // 기존 데이터가 없으면 새로 저장
                    accidentRepository.save(newAccident);

                    // 특정 장소에 20개 초과 데이터가 있으면 오래된 데이터 삭제 (단, exp_clr_dt가 지난 것 중에서만 삭제)
                    int areaId = newAccident.getArea().getId();
                    List<Accident> expiredRecords = accidentRepository.findOldestRecordsByArea(areaId, currentTime);
                    Long count = accidentRepository.countByAreaId(areaId);

                    if (count > 20 && !expiredRecords.isEmpty()) {
                        accidentRepository.delete(expiredRecords.get(0)); // exp_clr_dt가 지난 데이터 중 가장 오래된 것 삭제
                    }
                }

            } catch (Exception e) {
                log.error("Error saving accident data: {}", newAccident, e);
            }
        }

        log.info("Processed {} accident records.", accidentDataList.size());
    }
}