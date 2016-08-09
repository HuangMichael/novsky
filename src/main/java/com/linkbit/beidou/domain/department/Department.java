package com.linkbit.beidou.domain.department;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;

import javax.annotation.Resource;
import javax.persistence.*;
import java.util.Date;

/**
 * Created by huangbin on 2016/03/14 0023.
 * 部门信息
 */
@Resource
@Entity
@Table(name = "T_DEPARTMENT")
@Data
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Department implements java.io.Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    //Fields
    @Column(length = 20, unique = true, nullable = false)
    private String deptNum;        //部门
    @Column(length = 200)
    private String description;
    @Temporal(TemporalType.DATE)                           //部门描述
    private Date startDate;               //开始时间
    @Temporal(TemporalType.DATE)
    private Date endDate;
    @Column(length = 1)
    private String hasChild; //0为无
    @Column(length = 20)//结束时间
    private String supervisor;          //负责人
    @JsonManagedReference
    @ManyToOne(optional = true, fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id", referencedColumnName = "id")
    Department parent;
    @Column(length = 1)
    String depType; //1为内部  2 为外部
    @Column(length = 1)
    private String status;
    private Long sortNo; //排序
}