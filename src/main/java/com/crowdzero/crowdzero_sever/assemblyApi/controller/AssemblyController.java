package com.crowdzero.crowdzero_sever.assemblyApi.controller;

import com.crowdzero.crowdzero_sever.assemblyApi.dto.AssemblyResponseDto;
import com.crowdzero.crowdzero_sever.assemblyApi.service.AssemblyService;
import com.crowdzero.crowdzero_sever.populationApi.dto.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/assembly")
public class AssemblyController {
    private final AssemblyService assemblyService;


    //집회 정보 get api
    @GetMapping("/{date}")
    public ApiResponse<List<AssemblyResponseDto>> getAssembliesByDate(@PathVariable String date) {
        String formattedDate = formatDate(date);

        List<AssemblyResponseDto> assemblyListData = assemblyService.getAssembliesByDate(formattedDate);
        if (assemblyListData.isEmpty()) {   //날짜가 DB에 없을 때
            String notAssemblyMsg = "해당 날짜에 대한 집회 정보가 없습니다.";
            return ApiResponse.badRequest(notAssemblyMsg);
        }
        return ApiResponse.success(assemblyListData);
    }

    /**
     * "yyyy-MM-dd" → "yyyy년 M월 d일 (요일)" 형식으로 변환
     */
    private String formatDate(String date) {
        try {
            LocalDate localDate = LocalDate.parse(date, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            DayOfWeek dayOfWeek = localDate.getDayOfWeek();
            String koreanDay = getKoreanDayOfWeek(dayOfWeek);
            return localDate.format(DateTimeFormatter.ofPattern("yyyy년 M월 d일")) + " (" + koreanDay + ")";
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("잘못된 날짜 형식입니다. (예: 2025-02-16)");
        }
    }

    // 요일 변환 (월~일)
    private String getKoreanDayOfWeek(DayOfWeek dayOfWeek) {
        switch (dayOfWeek) {
            case MONDAY: return "월";
            case TUESDAY: return "화";
            case WEDNESDAY: return "수";
            case THURSDAY: return "목";
            case FRIDAY: return "금";
            case SATURDAY: return "토";
            case SUNDAY: return "일";
            default: return "";
        }
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

}
