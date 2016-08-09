package com.linkbit.beidou.domain.consumables;

import lombok.*;

import javax.annotation.Resource;
import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by Administrator on 2016/4/27.
 * <p/>
 * 易耗品管理
 */
@Resource
@Entity
@Table(name = "T_CONSUMABLES")
@Data
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Consumables implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(length = 20, unique = true, nullable = false)
    private String consNo;
    @Column(length = 20)
    private String description;
    @Column(length = 20)
    private String unit;
    private Double price;
    private Integer amount;
    @Column(length = 1)
    private String status;

    @Column(length = 1)
    private String type;  //1为易耗品  2为设备配件

}
