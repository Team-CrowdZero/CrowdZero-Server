package com.crowdzero.crowdzero_sever.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

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
}