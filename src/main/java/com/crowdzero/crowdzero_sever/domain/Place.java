package com.crowdzero.crowdzero_sever.domain;

import jakarta.persistence.*;

@Entity
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

    public Place(int id, String areaCd, String areaNm, String imageUrl) {
        this.id = id;
        this.areaCd = areaCd;
        this.areaNm = areaNm;
        this.imageUrl = imageUrl;
    }

    public int getId() {
        return id;
    }

    public String getAreaNm() {
        return areaNm;
    }

    public String getAreaCd() {
        return areaCd;
    }

    public String getImageUrl() {
        return imageUrl;
    }
}