package com.linkbit.beidou.domain.role;


import com.linkbit.beidou.domain.user.User;
import lombok.*;

import javax.persistence.*;
import java.util.Set;

/**
 * Created by huangbin on 2016/03/14 0023.
 * 角色信息
 */
@Entity
@Table(name = "T_ROLE")
@Data
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @Column(length = 20, unique = true, nullable = false)
    private String roleName;
    @Column(length = 50, unique = true, nullable = false)
    private String roleDesc;
    @Column(scale = 1000, nullable = true)
    private long sortNo;
    @Column(nullable = false, length = 1)
    private String status;
    @ManyToMany
    @JoinTable(name = "t_user_role", joinColumns = {@JoinColumn(name = "user_id")}, inverseJoinColumns = {@JoinColumn(name = "role_id")})
    private Set<User> userList;
}

