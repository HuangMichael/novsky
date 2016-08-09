package com.linkbit.beidou.domain.app.dataFilter;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by HUANGBIN on 2016/4/15.
 * 数据过滤器
 */

@Entity
@Table(name = "T_DATA_FILTER")
@Data
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class DataFilter {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(length = 20, unique = true, nullable = false)
    private String entityName;//表名称
    @Column(length = 20)
    private String description;//表名称
    @OneToMany(targetEntity = DataFilter.class, cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    List<DataFilter> dataFilterList = new ArrayList<DataFilter>();
    @Column(length = 1)
    private String status;
    private Long sortNo; //排序
}
