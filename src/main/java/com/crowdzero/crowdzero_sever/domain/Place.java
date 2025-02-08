package com.crowdzero.crowdzero_sever.domain;

import jakarta.persistence.*;

@Entit
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

    @Column(name = "image_url")
    private String imageUrl;

    public Place() {
    }
}