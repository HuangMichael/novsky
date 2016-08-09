package com.linkbit.beidou.domain.app.dataFilter;

import lombok.*;

import javax.persistence.*;

/**
 * Created by HUANGBIN on 2016/4/15.
 * 数据过滤器
 */

@Entity
@Table(name = "T_DATA_FILTER_CONDITION")
@Data
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class DataFilterCondition {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(length = 20, unique = true, nullable = false)
    private String description;//表名称
    @Column(length = 200)
    private String condition;//过滤条件
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(referencedColumnName = "id", name = "filter_id")
    private DataFilter dataFilter;
    @Column(length = 1)
    private String status;
    private Long sortNo; //排序
}
