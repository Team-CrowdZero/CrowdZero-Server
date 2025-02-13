package com.crowdzero.crowdzero_sever.accidentApi.web.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public class AccidentSaveDto {
    @JsonProperty("ACDNT_OCCR_DT")
    private String accidentOccrDt;

    @JsonProperty("EXP_CLR_DT")
    private String expClrDt;

    @JsonProperty("ACDNT_INFO")
    private String accidentInfo;

    @JsonProperty("ACDNT_X")
    private double accidentX;

    @JsonProperty("ACDNT_Y")
    private double accidentY;

    @JsonProperty("ACDNT_TIME")
    private String accidentTime;

    public AccidentSaveDto(String accidentOccrDt, String expClrDt, String accidentInfo, double accidentX, double accidentY, String accidentTime) {
        this.accidentOccrDt = accidentOccrDt;
        this.expClrDt = expClrDt;
        this.accidentInfo = accidentInfo;
        this.accidentX = accidentX;
        this.accidentY = accidentY;
        this.accidentTime = accidentTime;
    }
}