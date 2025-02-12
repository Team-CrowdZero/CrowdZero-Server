package com.crowdzero.crowdzero_sever.populationApi.dto;

import com.crowdzero.crowdzero_sever.populationApi.domain.Population;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class PopulationResponseDto {

    private Integer areaId;
    private String areaNm;
    private String areaCongestLvl;
    private String areaCongestMsg;
    private Integer areaPpltnMin;
    private Integer areaPpltnMax;
    private String ppltnTime;


    public static PopulationResponseDto from(Population population) {
        return PopulationResponseDto.builder()
                .areaId(population.getPlace().getId())
                .areaNm(population.getPlace().getAreaNm())
                .areaCongestLvl(population.getAreaCongestLvl())
                .areaCongestMsg(population.getAreaCongestMsg())
                .areaPpltnMin(population.getAreaPpltnMin())
                .areaPpltnMax(population.getAreaPpltnMax())
                .ppltnTime(population.getPpltnTime())
                .build();
    }
}
