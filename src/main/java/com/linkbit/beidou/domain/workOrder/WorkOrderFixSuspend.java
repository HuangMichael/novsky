package com.linkbit.beidou.domain.workOrder;

import com.linkbit.beidou.domain.equipments.Equipments;
import com.linkbit.beidou.domain.equipments.EquipmentsClassification;
import com.linkbit.beidou.domain.locations.Locations;
import com.linkbit.beidou.domain.locations.Vlocations;
import com.linkbit.beidou.domain.outsourcingUnit.OutsourcingUnit;
import lombok.*;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "T_WORK_ORDER_Fix_Suspend")
@Data
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
/**
 * 报修工单信息明细
 * */
public class WorkOrderFixSuspend {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @Column(length = 20, unique = true, nullable = false)
    private String orderLineNo; //工单编号行号
    @Column(length = 100)
    private String orderDesc;  //故障描述
    @Column(length = 100)
    private String fixDesc;  //故障描述
    @OneToOne
    @JoinColumn(name = "locations_id")
    private Locations locations;
    @ManyToOne(optional = true, fetch = FetchType.LAZY)
    @JoinColumn(name = "fix_id", referencedColumnName = "id")
    private WorkOrderFix workOrderFix;
    @OneToOne
    @JoinColumn(name = "equipments_id")
    private Equipments equipments;
    @OneToOne
    @JoinColumn(name = "eqClass_id")
    private EquipmentsClassification equipmentsClassification;

    @Column(length = 20)
    String maintainer;

    @ManyToOne(optional = true, fetch = FetchType.LAZY)
    @JoinColumn(name = "unit_id", referencedColumnName = "id")
    OutsourcingUnit unit;

    @Column(length = 1)
    private String reportType; //报修方式 S为设备 W位置

    @Temporal(TemporalType.TIMESTAMP)
    private Date reportTime; //状态时间

    @Column(length = 20)
    String location;

    @Column(length = 1)
    String status;

    @org.springframework.data.annotation.Transient
    @ManyToOne(optional = true, fetch = FetchType.LAZY)
    @JoinColumn(name = "vlocations_id", referencedColumnName = "id")
    private Vlocations vlocations;  //所属位置
}
