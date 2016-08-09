package com.linkbit.beidou.domain.workOrder;

import com.linkbit.beidou.domain.consumables.Consumables;
import lombok.*;

import javax.persistence.*;

/**
 * Created by huangbin on 2016/4/29.
 * 工单耗材
 */
@Entity
@Table(name = "T_WORK_ORDER_CONSUMPTIVE_MATERIAL")
@Data
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ConsumptiveMaterial {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @Column(length = 20, nullable = false)
    private String consNo; //耗材编号
    @Column(length = 20, nullable = false)
    private long amount; //耗材数量
    @Column(length = 1)
    private String status; //状态
    @ManyToOne(optional = true, fetch = FetchType.LAZY)
    @JoinColumn(name = "consumables_id", referencedColumnName = "id")
    private Consumables consumables; //配件信息

}
