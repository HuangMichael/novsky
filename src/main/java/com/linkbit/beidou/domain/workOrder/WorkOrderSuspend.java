package com.linkbit.beidou.domain.workOrder;

import lombok.*;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "T_WORK_ORDER_SUSPEND")
@Data
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
/**
 * 工单维修信息
 * */
public class WorkOrderSuspend {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String suspendReason;  //挂起原因
    @Temporal(TemporalType.TIMESTAMP)
    private Date suspendTime;  //挂起时间



}
