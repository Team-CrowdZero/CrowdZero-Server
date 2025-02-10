package com.crowdzero.crowdzero_sever.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Table(name = "accident")
public class Accident {
    @Id
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
    private Double acdntTime;

    @ManyToOne
    @JoinColumn(name = "area_id", referencedColumnName = "id")
    Place area;
}
