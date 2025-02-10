package com.crowdzero.crowdzero_sever.accidentApi.domain;

import com.crowdzero.crowdzero_sever.domain.Place;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@Getter
@Table(name = "accident")
public class Accident {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Id 자동 생성
    private Long id;

    @Column(name = "acdnt_occr_dt") // 사고 발생 일시
    private String acdntOccrDt;

    @Column(name = "exp_clr_dt") // 통제종료예정일시
    private String expClrDt;

    @Column(name = "acdnt_info") // 사고 통제 내용
    private String acdntInfo;

    @Column(name = "acdnt_x") // 사고 통제 지점 X 좌표
    private Double acdntX;

    @Column(name = "acdnt_y") // 사고 통제 지점 X 좌표
    private Double acdntY;

    @Column(name = "acdnt_time")
    private String acdntTime;

    @ManyToOne
    @JoinColumn(name = "area_id", referencedColumnName = "id")
    Place area;

    @Builder // id 자동생성이기도 하고, 항목이 많아 builder 어노테이션 사용하는 게 용이할 것 같아 사용했습니다.
    public Accident(String acdntOccrDt, String expClrDt, String acdntInfo, Double acdntX, Double acdntY, String acdntTime, Place area) {
        this.acdntOccrDt = acdntOccrDt;
        this.expClrDt = expClrDt;
        this.acdntInfo = acdntInfo;
        this.acdntX = acdntX;
        this.acdntY = acdntY;
        this.acdntTime = acdntTime;
        this.area = area;
    }
}
