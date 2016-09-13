package com.linkbit.beidou.domain.EcBudget;

import com.linkbit.beidou.domain.equipments.EquipmentsClassification;
import com.linkbit.beidou.domain.locations.Vlocations;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;

/**
 * 易耗品采购申请单
 **/
@Entity
@Table(name = "T_EC_BUDGET_BILL")
@Data
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class EcBudgetBill {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;  //id

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private Date applyDate;// 申请日期

    @Column(length = 20)
    private String applicant;// 填报人


}
