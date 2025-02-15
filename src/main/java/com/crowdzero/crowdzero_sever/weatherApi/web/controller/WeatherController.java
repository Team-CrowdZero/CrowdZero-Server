package com.crowdzero.crowdzero_sever.weatherApi.web.controller;

import com.crowdzero.crowdzero_sever.accidentApi.service.AccidentService;
import com.crowdzero.crowdzero_sever.accidentApi.web.dto.AccidentResponseDto;
import com.crowdzero.crowdzero_sever.common.ApiResponse;
import com.crowdzero.crowdzero_sever.weatherApi.service.WeatherService;
import com.crowdzero.crowdzero_sever.weatherApi.web.dto.WeatherResponseDto;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/weather")
public class WeatherController {
    private final WeatherService weatherService;

    public WeatherController(WeatherService weatherService) {
        this.weatherService = weatherService;
    }

    @GetMapping("/{areaId}")
    public ApiResponse<WeatherResponseDto> getWeatherByArea(@PathVariable Long areaId) {
        try {
            validateAreaId(areaId);

            WeatherResponseDto responseDto = weatherService.getWeatherByAreaId(areaId);
            return responseDto == null ? ApiResponse.successWithoutData() : ApiResponse.success(responseDto);

        } catch (IllegalArgumentException e) {
            return ApiResponse.badRequest(e.getMessage());

        } catch (RuntimeException e) {
            return ApiResponse.internalServerError();

        } catch (Exception e) {
            return ApiResponse.error(520, "알 수 없는 오류가 발생했습니다: " + e.getMessage());
        }
    }

    private void validateAreaId(Long areaId) {
        if (areaId == null || areaId <= 0 || areaId > 5) {
            throw new IllegalArgumentException("존재하지 않는 장소입니다.");
        }
    }
}