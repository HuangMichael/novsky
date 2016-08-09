package com.linkbit.beidou.domain.workOrder;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "T_WORK_ORDER_FIX")
@Data
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
/**
 * 维修单信息
 * */
public class WorkOrderFix {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @Column(length = 20, unique = true, nullable = false)
    private String orderNo; //工单编号
    @Column(length = 200)
    private String orderDesc; //工单描述
    @Column(length = 20)
    private String reporter; //报告人
    @Temporal(TemporalType.TIMESTAMP)
    private Date reportTime; //报告时间
    @Column(length = 15)
    private long sortNo;
    @Column(length = 1)
    private String status;
    @Column(length = 20)
    private String location;

    @JsonBackReference("workOrderFixDetailList")
    @OneToMany(targetEntity = WorkOrderFixDetail.class, cascade = CascadeType.ALL, mappedBy = "workOrderFix")
    List<WorkOrderFixDetail> workOrderFixDetailList = new ArrayList<WorkOrderFixDetail>();
}
