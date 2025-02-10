package com.crowdzero.crowdzero_sever.populationApi.dto;

import com.crowdzero.crowdzero_sever.populationApi.domain.population;

public class RequestDto {
    private Long areaId;

    public population toEntity() {
        return population.builder()
                .areaId(areaId)
                .build();
    }
}
