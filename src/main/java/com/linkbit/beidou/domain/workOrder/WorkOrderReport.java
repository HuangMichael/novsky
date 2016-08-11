package com.linkbit.beidou.domain.workOrder;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;

import javax.persistence.*;
import javax.persistence.criteria.Fetch;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "T_WORK_ORDER_REPORT")
@Data
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
/**
 * 报修工单信息
 * */
public class WorkOrderReport {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @Column(length = 20, unique = true, nullable = false)
    private String orderNo; //工单编号
    @Column(length = 20, nullable = false)
    private String reporter; //报告人
    @Temporal(TemporalType.TIMESTAMP)
    private Date reportTime; //报告时间
    @Column(length = 1)
    private String status;
    @Column(length = 20, nullable = false)
    private String location;

}
