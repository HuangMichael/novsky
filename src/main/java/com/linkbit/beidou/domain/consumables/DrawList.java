package com.linkbit.beidou.domain.consumables;

import lombok.*;

import javax.annotation.Resource;
import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by Administrator on 2016/4/27.
 * <p/>
 * 易耗品领取记录
 */
@Resource
@Entity
@Table(name = "T_DRAW_LIST")
@Data
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class DrawList implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(length = 20, nullable = false)
    private String drawer; //领取人
    @Column(length = 20, nullable = false)
    private String handler; //经手人
    @Temporal(TemporalType.TIMESTAMP)
    private Date handleTime; //领取时间
    private Integer amount;  //领取数量
    @Column(length = 1)
    private String type;  //1为易耗品  2为设备配件
    @OneToOne
    @JoinColumn(name = "consumables_id")
    private Consumables consumables;

}
