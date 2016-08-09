package com.linkbit.beidou.domain.workOrder;

import com.linkbit.beidou.domain.outsourcingUnit.OutsourcingUnit;
import lombok.*;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "T_WORK_ORDER_MAINTENANCE")
@Data
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
/**
 * 工单维修信息
 * */
public class WorkOrderMaintenance {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "unit_id", referencedColumnName = "id")
    private OutsourcingUnit outsourcingUnit;
    @Temporal(TemporalType.TIMESTAMP)
    private Date beginTime; //维修开始时间
    @Temporal(TemporalType.TIMESTAMP)
    private Date endTime; //维修结束时间
    private Long limitedHours;
    private Double assessLevel;  //评价等级

}
