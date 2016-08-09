package com.linkbit.beidou.domain.workOrder;

import lombok.*;

import javax.persistence.*;
import java.util.Date;

/**
 *
 */
@Entity
@Table(name = "v_work_order_finish_ratio")
@Data
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
/**
 * 工单报修完成率
 * */
public class VworkOrderFinishRatio {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String orderLineNo;
    private String location;
    @Temporal(TemporalType.DATE)
    private Date reportTime;
    @Temporal(TemporalType.DATE)
    private Date finishTime;
}
