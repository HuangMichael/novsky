package com.linkbit.beidou.domain.workOrder;

import com.linkbit.beidou.domain.equipments.Equipments;
import com.linkbit.beidou.domain.equipments.EquipmentsClassification;
import com.linkbit.beidou.domain.locations.Locations;
import com.linkbit.beidou.domain.locations.Vlocations;
import com.linkbit.beidou.domain.outsourcingUnit.OutsourcingUnit;
import lombok.*;
import org.springframework.data.annotation.*;

import javax.persistence.*;
import javax.persistence.Id;
import javax.persistence.Transient;
import java.util.Date;

@Entity
@Table(name = "T_WORK_ORDER_REPORT_DETAIL")
@Data
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
/**
 * 报修工单信息明细
 * */
public class WorkOrderReportDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @Column(length = 20, unique = true, nullable = false)
    private String orderLineNo; //工单编号行号
    private String orderDesc;  //故障描述
    @OneToOne
    @JoinColumn(name = "locations_id")
    private Locations locations;

    @OneToOne
    @JoinColumn(name = "report_id")
    private WorkOrderReport workOrderReport;
    @OneToOne
    @JoinColumn(name = "equipments_id")
    private Equipments equipments;
    @OneToOne
    @JoinColumn(name = "eqClass_id")
    private EquipmentsClassification equipmentsClassification;

    @Column(length = 1)
    private String reportType; //报修方式 S为设备 W位置


    @ManyToOne(optional = true, fetch = FetchType.LAZY)
    @JoinColumn(name = "unit_id", referencedColumnName = "id")
    OutsourcingUnit unit;


    @Temporal(TemporalType.TIMESTAMP)
    private Date reportTime; //状态时间


    @Column(length = 20)
    private String location; //位置编号


    @Column(length = 1)
    String status;

    @org.springframework.data.annotation.Transient
    @ManyToOne(optional = true, fetch = FetchType.LAZY)
    @JoinColumn(name = "vlocations_id", referencedColumnName = "id")
    private Vlocations vlocations;  //所属位置
}
