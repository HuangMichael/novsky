package com.linkbit.beidou.domain.preMaint;


import com.linkbit.beidou.domain.equipments.Equipments;
import com.linkbit.beidou.domain.locations.Vlocations;
import com.linkbit.beidou.domain.outsourcingUnit.OutsourcingUnit;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by huangbin on 2016年10月9日11:42:09
 * 预防性维修
 */
@Entity
@Table(name = "T_PRE_MAINT")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PreMaint {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;  //id

    @Column(length = 20, unique = true)
    private String pmCode; //设备编号


    @Column(length = 20)
    private String description; //设备描述


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "location_id", referencedColumnName = "id")
    private Vlocations locations;  //位置


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "equipment_id", referencedColumnName = "id")
    private Equipments equipment;  //设备

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "unit_id", referencedColumnName = "id")
    private OutsourcingUnit outUnit; //外委单位


    @Column(length = 3)
    private int frequency; //频率

    @Column(length = 1)
    private int unit; //单位


    @Column(length = 1, columnDefinition = "default 1")
    private String status; // 1 已执行 0未执行

    @Column(length = 20)
    private String location; //加入冗余字段location 方便模糊查询


    @Column(length = 20)
    private String createBy; //创建人


    @Column(length = 20)
    private String createTime; //创建时间


    @Column(length = 20)
    private String latestTime; //最近执行时间

    @Column(length = 20)
    private String nextTime; //下个时间
}
