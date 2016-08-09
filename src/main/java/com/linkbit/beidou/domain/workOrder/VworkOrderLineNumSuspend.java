package com.linkbit.beidou.domain.workOrder;

import lombok.*;

import javax.persistence.*;


@Entity
@Table(name = "v_work_order_line_num_suspend")
@Data
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
/**
 * 工单报修完成率
 * */
public class VworkOrderLineNumSuspend {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String line;
    private Long suspendNum;
}
