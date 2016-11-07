package com.linkbit.beidou.object.statistics;/**
 * Created by Administrator on 2016/11/7.
 */

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/**
 * 统计结果
 *
 * @author
 * @create 2016-11-07 10:20
 **/
@Entity
@Table(name = "v_work_order_units_order_finished_count")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class StatisticsDistributedObject {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long unitId;
    private String reportYear;
    private String reportMonth;
    private Long reportNum;
}
