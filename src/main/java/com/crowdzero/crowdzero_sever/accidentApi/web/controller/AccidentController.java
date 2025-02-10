package com.crowdzero.crowdzero_sever.accidentApi.web.controller;

import com.crowdzero.crowdzero_sever.accidentApi.service.AccidentService;
import com.crowdzero.crowdzero_sever.accidentApi.web.dto.AccidentResponseDto;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/acdnt")
public class AccidentController {
    private final AccidentService accidentService; // TODO: service 의존성 주입

    public AccidentController(AccidentService accidentService) {
        this.accidentService = accidentService;
    }

    @GetMapping("/{areaId}")
    public AccidentResponseDto findByAreaId(@PathVariable("areaId") int areaId) {
        return new AccidentResponseDto(); // TODO: Service 로직 짠 이후 코드 변경
    }
}