package com.subway.domain.line;

import lombok.*;

import javax.annotation.Resource;
import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by huangbin on 2016/03/14 0023.
 * 线路信息
 */
@Entity
@Table(name = "V_STATION")
@Data
public class Station implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    //Fields
    @Column(length = 20, unique = true, nullable = false)
    private String stationNo;
    @Column(length = 200)
    private String description;
    @Column(length = 1)
    private String status;
    private Long sortNo; //排序
    @Column(length = 1)
    private String type;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "line_id", referencedColumnName = "id")
    Line line;

}