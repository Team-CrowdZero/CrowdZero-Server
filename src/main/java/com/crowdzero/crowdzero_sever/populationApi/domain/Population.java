package com.crowdzero.crowdzero_sever.populationApi.domain;

import com.crowdzero.crowdzero_sever.domain.Place;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Population {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="place_id")
    private Place place;      //장소 객체 외래키

    @Builder
    public Population(String areaCongestLvl, String areaCongestMsg, Integer areaPpltnMax, Integer areaPpltnMin, String ppltnTime, Place place) {
        this.areaCongestLvl = areaCongestLvl;
        this.areaCongestMsg = areaCongestMsg;
        this.areaPpltnMax = areaPpltnMax;
        this.areaPpltnMin = areaPpltnMin;
        this.ppltnTime = ppltnTime;
        this.place = place;
    }

    public void update(Population newPopulation) {
        this.areaCongestLvl = newPopulation.areaCongestLvl;
        this.areaCongestMsg = newPopulation.areaCongestMsg;
        this.areaPpltnMax = newPopulation.areaPpltnMax;
        this.areaPpltnMin = newPopulation.areaPpltnMin;
        this.ppltnTime = newPopulation.ppltnTime;
        this.place = newPopulation.place;
    }

}
