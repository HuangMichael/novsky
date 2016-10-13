package com.linkbit.beidou.domain.workOrder;

import lombok.*;

import javax.persistence.*;


@Entity
@Table(name = "V_line_Month_Object")
@Data
@AllArgsConstructor
@NoArgsConstructor
/**
 * 按照线路月统计工单状态
 * */
public class VlineMonthObject {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String line;
    private String reportMonth;
    private Long num;
    private String nodeState;
}
