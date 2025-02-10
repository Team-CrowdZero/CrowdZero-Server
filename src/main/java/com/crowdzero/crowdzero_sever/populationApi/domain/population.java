package com.crowdzero.crowdzero_sever.populationApi.domain;


import com.fasterxml.jackson.annotation.JsonAnyGetter;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class population {

    @Id
    private Long id;

    @Column(name = "area_congest_lvl")//인구 혼잡도 지표
    private String areaCongestLvl;

    @Column(name = "area_congest_msg")//인구 혼잡도 지표
    private String areaCongestMsg;

    @Column(name = "area_ppltn_max")//실시간 인구 최대값
    private Integer areaPpltnMax;

    @Column(name = "area_ppltn_min")//실시간 인구 최소값
    private Integer areaPpltnMin;

    @Column(name = "ppltn_time") //마지막 업데이트 시간
    private String ppltnTime;

    @Column(name = "area_id")
    private String areaId;      //장소 아이디


}
