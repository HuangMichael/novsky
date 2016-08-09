package com.linkbit.beidou.domain.equipments;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;

import javax.persistence.*;

/**
 * Created by huangbin on 2016/03/14 0023.
 * 设备技术参数信息
 */
@Entity
@Table(name = "T_EQUIPMENTS_SPECIFICATIONS")
@Data
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class EquipmentsSpecifications {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(length = 50)
    private String attrName;  //设备参数名称
    @Column(length = 10)
    private String unit; //单位
    @Column(length = 100)
    private String remark;  //设备备注
    @JsonBackReference("equipmentsClassification")
    @ManyToOne(optional = true, fetch = FetchType.LAZY)
    @JoinColumn(name = "equip_class_id", referencedColumnName = "id")
    EquipmentsClassification equipmentsClassification;

    @OneToOne //JPA注释： 一对一 关系
    @JoinColumn(name="equipments_spec_detail_id" ,referencedColumnName = "id")
    EquipmentsSpecDetail equipmentsSpecDetail;
    private Long sortNo;


}
