package com.linkbit.beidou.domain.budget;

import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;

/**
 * 采购申请单
 *
 * @author
 * @create 2016-09-09 11:03
 **/
@Entity
@Table(name = "T_BUDGET_BILL")
@Data
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class BudgetBill {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;  //id

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private Date applyDate;// 申购日期

    @Column(length = 50)
    private String accessoryName; //配件名称

    @Column(length = 50)
    private String applyDep; //申购部门

    @Column(length = 50)
    private String applicant; //申请人

    @Column(length = 50)
    private String purpose; //用途

    @Column(length = 50)
    private String specifications; //规格

    @Column(length = 10)
    private Long amount; //采购数量

    @Column(length = 10)
    private String approver; //批准人

    @Column(length = 10)
    private String handler; //经办人

    @Column(length = 10)
    private String receiver; //接收人


}
