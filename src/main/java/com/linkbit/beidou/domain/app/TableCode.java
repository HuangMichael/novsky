package com.linkbit.beidou.domain.app;


import lombok.*;

import javax.persistence.*;

/**
 * Created by huangbin on 2016/03/14 0023.
 * 编码生成信息
 */
@Entity
@Table(name = "T_TABLE_CODE")
@Data
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class TableCode implements java.io.Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(length = 20, unique = true, nullable = false)
    private String entityName;//表名称
    @Column(length = 20)
    private String prefix;//前缀
    @Column(length = 1)
    private String status;
    private Long sortNo; //排序
}