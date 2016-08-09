package com.linkbit.beidou.domain.app.resoure;

import lombok.*;

import javax.persistence.*;

/**
 * Created by HUANGBIN on 2016/4/15.
 * 资源信息
 */

@Entity
@Table(name = "T_RESOURCE")
@Data
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Resource {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(length = 20, nullable = false)
    private String resourceName;//资源名称
    @Column(length = 20, nullable = false)
    private String resourceUrl;//资源路径
    @Column(length = 50, nullable = false)
    private String description;//表名称
    @Column(length = 1)
    private Long resourceLevel;//资源级别
    @ManyToOne
    @JoinColumn(name = "parent_id", referencedColumnName = "id")
    Resource parent;
    @Column(length = 1)
    private String status;
    private Long sortNo; //排序
    @Column(length = 1)
    private boolean staticFlag;
}
