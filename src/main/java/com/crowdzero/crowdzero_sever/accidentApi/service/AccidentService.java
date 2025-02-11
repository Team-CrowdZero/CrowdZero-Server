package com.crowdzero.crowdzero_sever.accidentApi.service;

import com.crowdzero.crowdzero_sever.accidentApi.domain.Accident;
import com.crowdzero.crowdzero_sever.accidentApi.domain.AccidentRepository;
import com.crowdzero.crowdzero_sever.accidentApi.web.dto.AccidentResponseDto;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class AccidentService {
    private AccidentRepository accidentRepository;

    public List<AccidentResponseDto> getTodayAccidentsByAreaId(Long areaId) {
        String currentDate = LocalDate.now().toString(); // "2025-02-11" 형식
        return accidentRepository.findByAreaIdAndCurrentDate(areaId, currentDate)
                .stream()
                .map(AccidentResponseDto::new)
                .toList();
    }
}
