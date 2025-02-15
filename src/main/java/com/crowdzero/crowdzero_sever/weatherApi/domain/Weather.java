package com.crowdzero.crowdzero_sever.weatherApi.domain;

import com.crowdzero.crowdzero_sever.domain.Place;
import com.crowdzero.crowdzero_sever.populationApi.domain.Population;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@Getter
@Table(name = "weather")
public class Weather {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Id 자동 생성
    private Long id;

    @Column(name = "sky_stts")
    private String skyStts;

    @Column(name = "temp")
    private int temp;

    @Column(name = "pm25_index")
    private String pm25Index;

    @Column(name = "pm10_index")
    private String pm10Index;

    @ManyToOne
    @JoinColumn(name = "area_id", referencedColumnName = "id")
    Place area;

    @Builder
    public Weather(String skyStts, int temp, String pm25Index, String pm10Index, Place area) {
        this.skyStts = skyStts;
        this.temp = temp;
        this.pm25Index = pm25Index;
        this.pm10Index = pm10Index;
        this.area = area;
    }

    public void update(int temp, String skyStts, String pm25Index, String pm10Index) {
        this.temp = temp;
        this.skyStts = skyStts;
        this.pm25Index = pm25Index;
        this.pm10Index = pm10Index;
    }
}
