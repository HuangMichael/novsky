package com.linkbit.beidou.domain.workOrder;

import lombok.*;

import javax.persistence.*;


@Entity
@Table(name = "v_work_order_fix_bill")
@Data
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
/**
 * 维修单视图实体类
 * */
public class VworkOrderFixBill {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String orderLineNo;
    private String orderDesc;
    private String eqName;
    private String locName;
    private String eqClass;
    private String nodeTime;
    private String deadLine;
    private String nodeState;
    private String location;
}
