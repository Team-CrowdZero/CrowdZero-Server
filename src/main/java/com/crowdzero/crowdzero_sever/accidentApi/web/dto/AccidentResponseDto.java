package com.crowdzero.crowdzero_sever.accidentApi.web.dto;

import com.crowdzero.crowdzero_sever.accidentApi.domain.Accident;
import lombok.Getter;

@Getter
public class AccidentResponseDto {
    private Long id;
    private String acdntOccrDt;
    private String expClrDt;
    private String acdntInfo;
    private Double acdntX;
    private Double acdntY;
    private String acdntTime;
    private int areaId;

    public AccidentResponseDto(Accident accident) {
        this.id = accident.getId();
        this.acdntOccrDt = accident.getAcdntOccrDt();
        this.expClrDt = accident.getExpClrDt();
        this.acdntInfo = accident.getAcdntInfo();
        this.acdntX = accident.getAcdntX();
        this.acdntY = accident.getAcdntY();
        this.acdntTime = accident.getAcdntTime();
        this.areaId = accident.getArea().getId(); // 이게 맞는? 효율적인 코드일까요?
    }
}
