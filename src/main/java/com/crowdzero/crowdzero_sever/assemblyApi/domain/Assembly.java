package com.crowdzero.crowdzero_sever.assemblyApi.domain;


import com.crowdzero.crowdzero_sever.populationApi.domain.Population;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Assembly {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "date")          //날짜
    private String date;

    @Column(name = "assemblyTime")  //집회 일시
    private String assemblyTime;

    @Column(name = "assemblyPlace")//집회장소
    private String assemblyPlace;

    @Column(name = "assemblyPopulation")//신고 인원
    private Integer assemblyPopulation;

    @Column(name = "jurisdiction") //관할서
    private String jurisdiction;

    @Column(name = "district")
    private String district;    //행정구역(동)


    @Builder
    public Assembly(String date, String assemblyTime, String assemblyPlace, Integer assemblyPopulation, String jurisdiction, String district) {
        this.date = date;
        this.assemblyTime = assemblyTime;
        this.assemblyPlace = assemblyPlace;
        this.assemblyPopulation = assemblyPopulation;
        this.jurisdiction = jurisdiction;
        this.district = district;
    }

}
