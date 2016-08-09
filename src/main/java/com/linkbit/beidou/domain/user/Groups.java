package com.linkbit.beidou.domain.user;


import lombok.*;

import javax.persistence.*;
import java.util.Set;

/**
 * Created by huangbin on 2016/03/14 0023.
 * 用户组信息
 */
@Entity
@Table(name = "T_GROUP")
@Data
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Groups {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @Column(length = 20, unique = true, nullable = false)
    private String groupName;
    @Column(length = 50, nullable = true)
    private String description;
    @Column(scale = 1000)
    private long sortNo;
    @Column(nullable = false, length = 1)
    private String status;

    @ManyToMany
    @JoinTable(name = "t_user_group", joinColumns = {@JoinColumn(name = "user_id")}, inverseJoinColumns = {@JoinColumn(name = "group_id")})
    private Set<User> users;


}

