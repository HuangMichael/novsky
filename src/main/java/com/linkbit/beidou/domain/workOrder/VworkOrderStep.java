package com.linkbit.beidou.domain.workOrder;

import com.linkbit.beidou.domain.equipments.Equipments;
import com.linkbit.beidou.domain.locations.Locations;
import lombok.*;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "v_work_order_step")
@Data
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
/**
 * 工单信息
 * */
public class VworkOrderStep {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    String orderLineNo;
    @ManyToOne(optional = true, fetch = FetchType.EAGER)
    @JoinColumn(name = "equipments_id", referencedColumnName = "id")
    Equipments equipments;
    String orderDesc;
    Date reportTime;
    String status;
    String nodeState;
    @ManyToOne(optional = true, fetch = FetchType.EAGER)
    @JoinColumn(name = "locations_id", referencedColumnName = "id")
    Locations locations;
}
