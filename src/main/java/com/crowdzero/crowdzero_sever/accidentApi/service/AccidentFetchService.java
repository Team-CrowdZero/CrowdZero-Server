package com.crowdzero.crowdzero_sever.accidentApi.service;

import com.crowdzero.crowdzero_sever.accidentApi.domain.Accident;
import com.crowdzero.crowdzero_sever.accidentApi.domain.AccidentRepository;
import com.crowdzero.crowdzero_sever.domain.Place;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class AccidentFetchService {
    private final AccidentRepository accidentRepository;

    public void saveAccidentData(List<Accident> accidentDataList) {
        for (Accident newAccident : accidentDataList) {
            try {
                Optional<Accident> existingAccident = accidentRepository.findExistingAccident(
                        newAccident.getAcdntX(), newAccident.getAcdntY(),
                        newAccident.getArea().getId(), newAccident.getAcdntOccrDt()
                );

                if (existingAccident.isPresent()) {
                    // 기존 데이터가 있으면 업데이트
                    Accident accidentToUpdate = existingAccident.get();
                    accidentToUpdate.update(newAccident.getExpClrDt(),
                            newAccident.getAcdntInfo(),
                            newAccident.getAcdntTime());
                    accidentRepository.save(accidentToUpdate);
                } else {
                    // 기존 데이터가 없으면 새로 저장
                    accidentRepository.save(newAccident);
                }

            } catch (Exception e) {
                log.error("Error saving accident data: {}", newAccident, e);
            }
        }

        log.info("Processed {} accident records.", accidentDataList.size());
    }
}