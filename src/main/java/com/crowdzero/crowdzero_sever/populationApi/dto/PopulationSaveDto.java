package com.crowdzero.crowdzero_sever.populationApi.dto;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class PopulationSaveDto {
    private Integer areaId;
    private String areaNm;
    private String areaCongestLvl;
    private String areaCongestMsg;
    private Integer areaPpltnMin;
    private Integer areaPpltnMax;
    private String ppltnTime;
}
