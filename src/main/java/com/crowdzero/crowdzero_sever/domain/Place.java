package com.crowdzero.crowdzero_sever.domain;

import com.crowdzero.crowdzero_sever.populationApi.domain.Population;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Table(name = "place")
public class Place {
    @Id
    private int id;

    @Column(name = "area_nm")
    private String areaNm;

    @Column(name = "area_cd")
    private String areaCd;
  
    @OneToMany(mappedBy = "place", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Population> population = new ArrayList<>();

}
