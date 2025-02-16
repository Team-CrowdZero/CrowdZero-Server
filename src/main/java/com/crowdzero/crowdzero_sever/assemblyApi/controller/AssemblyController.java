package com.crowdzero.crowdzero_sever.assemblyApi.controller;

import com.crowdzero.crowdzero_sever.assemblyApi.dto.AssemblyResponseDto;
import com.crowdzero.crowdzero_sever.assemblyApi.service.AssemblyService;
import com.crowdzero.crowdzero_sever.populationApi.dto.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/assembly")
public class AssemblyController {
    private final AssemblyService assemblyService;

    /**
     * API에서 데이터를 가져와서 MySQL에 저장하는 엔드포인트
     */
    @PostMapping("/fetch")
    public String fetchAndSaveAssemblies() {
        assemblyService.fetchAndSaveAssemblies();
        return "집회 데이터가 저장되었습니다.";
    }

    @GetMapping("/{date}")
    public ApiResponse<List<AssemblyResponseDto>> getAssembliesByDate(@PathVariable String date) {
        validateDate(date);  // 컨트롤러에서 직접 유효성 검사
        List<AssemblyResponseDto> assemblyListData = assemblyService.getAssembliesByDate(date);
        return ApiResponse.success(assemblyListData);
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
     * 날짜 검증 로직
     */
    // TODO: 반환 값 api 명세서와 같이 써서 수정하기
    private void validateDate(String date) {
        List<AssemblyResponseDto> assemblies = assemblyService.getAssembliesByDate(date);
        if (assemblies.isEmpty()) {
            throw new IllegalArgumentException("해당 날짜에 대한 집회 데이터가 존재하지 않습니다: " + date);
        }
    }
}
