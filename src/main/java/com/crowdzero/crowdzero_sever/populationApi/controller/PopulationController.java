package com.crowdzero.crowdzero_sever.populationApi.controller;

import com.crowdzero.crowdzero_sever.populationApi.dto.ApiResponse;
import com.crowdzero.crowdzero_sever.populationApi.dto.PopulationResponseDto;
import com.crowdzero.crowdzero_sever.populationApi.service.PopulationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/ppltn")
public class PopulationController {
    private final PopulationService populationService;

    /* 테스트용 saveAPI
    @PostMapping
    public ResponseEntity<PopulationResponseDto> addPopulation(@RequestBody PopulationResponseDto populationResponseDto) {
        return new ResponseEntity<>(populationService.savePopulation(populationResponseDto), HttpStatus.CREATED);
    }
    */

    @GetMapping("/{areaId}")
    public ApiResponse<PopulationResponseDto> findByPopulationId(@PathVariable(name = "areaId") Integer areaId) {
        validateAreaId(areaId);  // 컨트롤러에서 직접 유효성 검사

        PopulationResponseDto populationData = populationService.findPopulationById(areaId);
        return ApiResponse.success(populationData);
    }

    /**
     * 숫자가 아닌 값이 들어올 때 (400 Bad Request)
     */
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ApiResponse<Object> handleTypeMismatchException() {
        return ApiResponse.error(400, "입력값이 올바르지 않습니다.");
    }

    /**
     * 존재하지 않는 장소 ID일 경우 (400 Bad Request)
     */
    @ExceptionHandler(IllegalArgumentException.class)
    public ApiResponse<Object> handleIllegalArgumentException(IllegalArgumentException e) {
        return ApiResponse.error(400, e.getMessage());
    }

    /**
     * 기타 런타임 예외 처리 (500 Internal Server Error)
     */
    @ExceptionHandler(RuntimeException.class)
    public ApiResponse<Object> handleRuntimeException(RuntimeException e) {
        return ApiResponse.error(500, "서버 내부 오류: " + e.getMessage());
    }

    /**
     * 그 외 모든 예외 처리 (520 Unknown Error)
     */
    @ExceptionHandler(Exception.class)
    public ApiResponse<Object> handleUnknownException(Exception e) {
        return ApiResponse.error(520, "알 수 없는 오류가 발생했습니다: " + e.getMessage());
    }

    /**
     * ID 범위 검증 로직
     */
    private void validateAreaId(Integer areaId) {
        if (areaId < 1 || areaId > 5) {
            throw new IllegalArgumentException("존재하지 않는 장소입니다.");
        }
    }
}

