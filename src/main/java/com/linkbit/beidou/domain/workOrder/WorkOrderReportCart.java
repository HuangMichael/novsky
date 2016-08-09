package com.linkbit.beidou.domain.workOrder;

import com.linkbit.beidou.domain.equipments.Equipments;
import com.linkbit.beidou.domain.equipments.EquipmentsClassification;
import com.linkbit.beidou.domain.locations.Locations;
import com.linkbit.beidou.domain.locations.Vlocations;
import lombok.*;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "T_WORK_ORDER_REPORT_CART")
@Data
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
/**
 * 报修车信息
 * */
public class WorkOrderReportCart {
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
    @JoinColumn(name = "equipments_id")
    private Equipments equipments;
    @OneToOne
    @JoinColumn(name = "eqClass_id")
    private EquipmentsClassification equipmentsClassification;
    @Column(length = 20)
    private String reporter; //报修人
    @Column(length = 20)
    private String creator; //录入人
    @Temporal(TemporalType.TIMESTAMP)
    private Date reportTime;  //故障描述
    @Column(length = 20)
    private String location; //位置编码
    @Column(length = 1)
    private String reportType; //报修方式 S为设备 W位置

    @Column(length = 10)
    private String nodeState; //节点状态

    @Column(length = 1)
    private String status;
    @ManyToOne(optional = true, fetch = FetchType.LAZY)
    @JoinColumn(name = "vlocations_id", referencedColumnName = "id")
    private Vlocations vlocations;  //所属位置
}
