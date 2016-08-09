package com.linkbit.beidou.domain.equipments;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;

import javax.persistence.*;

/**
 * Created by huangbin on 2016/03/14 0023.
 * 设备技术参数明细信息
 */
@Entity
@Table(name = "T_EQUIPMENTS_SPEC_DETAIL")
@Data
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class EquipmentsSpecDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;


    @JsonManagedReference
    @ManyToOne(optional = true, fetch = FetchType.LAZY)
    @JoinColumn(name = "equipments_id", referencedColumnName = "id")
    Equipments equipments;


    @OneToOne (mappedBy = "equipmentsSpecDetail")//JPA注释： 一对一 关系
    private EquipmentsSpecifications equipmentsSpecifications;//设备参数
    @Column(length = 20)
    private String value; //单位
    @Column(length = 100)
    private String remark;  //设备备注
    private Long sortNo;


}
