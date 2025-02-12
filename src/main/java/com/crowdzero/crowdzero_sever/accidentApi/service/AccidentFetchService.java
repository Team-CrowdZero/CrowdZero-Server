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
                    // 기존 데이터가 있으면 업데이트 - '종료시간', '정보', '최종업데이트시간' 업데이트
                    // 도로통제 기존 데이터 기준: 좌표값과 사고발생시간이 같은 경우
                    // 도로 통제의 경우 종료 시간이 바뀌는 경우가 있어서 업데이트로 했는데, API 경우에 맞게 이 부분은 빼거나 수정하면 될 것 같습니다.
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