package com.crowdzero.crowdzero_sever.weatherApi.web.dto;

import com.crowdzero.crowdzero_sever.weatherApi.domain.Weather;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class WeatherResponseDto {
    private Long id;
    private String areaNm;
    private String skyStts;
    private int temp;
    private String pm25Index;
    private String pm10Index;
    private int areaId;

    public WeatherResponseDto(Weather weather) {
        this.id = weather.getId();
        this.areaNm = weather.getArea().getAreaNm();
        this.skyStts = weather.getSkyStts();
        this.temp = weather.getTemp();
        this.pm25Index = weather.getPm25Index();
        this.pm10Index = weather.getPm10Index();
        this.areaId = weather.getArea().getId();
    }
}
