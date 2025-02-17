package com.crowdzero.crowdzero_sever.assemblyApi.dto;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class AssemblyResponseDto {
    private String date;
    private String assemblyTime;
    private String assemblyPlace;
    private Integer assemblyPopulation;
    private String jurisdiction;
    private String district;
}
