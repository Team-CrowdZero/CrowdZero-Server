package com.crowdzero.crowdzero_sever.accidentApi.service;

import com.crowdzero.crowdzero_sever.accidentApi.domain.Accident;
import com.crowdzero.crowdzero_sever.accidentApi.domain.AccidentRepository;
import com.crowdzero.crowdzero_sever.domain.Place;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class AccidentFetchService {
    private final AccidentRepository accidentRepository;

    public void saveAccidentData(List<Accident> accidentDataList) {
        for (Accident accident : accidentDataList) {
            try {
                accidentRepository.save(accident);
            } catch (Exception e) {
                log.error("Error saving accident data: {}", accident, e);
            }
        }

        log.info("Saved {} accident records to the database.", accidentDataList.size());
    }
}