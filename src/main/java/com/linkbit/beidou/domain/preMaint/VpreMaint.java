package com.linkbit.beidou.domain.preMaint;

import lombok.Data;

import javax.persistence.*;

/**
 * 预防性维修值对象视图
 *
 * @author
 * @create 2016-10-09 14:08
 **/
@Entity
@Table(name = "V_PRE_MAINTAIN")
@Data
public class VpreMaint {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;  //id

    @Column(length = 20)
    private String pmCode; //编号

    @Column(length = 20)
    private String pmDesc; //描述

    @Column(length = 20)
    private Long frequency;//频率

    @Column(length = 50)
    private String unit;//单位

    @Column(length = 50)
    private String locName;//位置

    @Column(length = 50)
    private String eqName;//设备名称

    @Column(length = 20)
    private String eqClass;//设备分类

    @Column(length = 50)
    private String outUnit; //外委单位

    @Column(length = 10)
    private String status; //状态
}
