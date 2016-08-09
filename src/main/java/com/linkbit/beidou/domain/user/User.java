package com.linkbit.beidou.domain.user;


import com.linkbit.beidou.domain.person.Person;
import com.linkbit.beidou.domain.role.Role;
import lombok.*;

import javax.persistence.*;
import java.util.Set;

/**
 * Created by huangbin on 2016/03/14 0023.
 * 用户信息
 */
@Entity
@Table(name = "T_USER")
@Data
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @Column(length = 20, unique = true, nullable = false)
    private String userName;
    @Column(length = 50, unique = false, nullable = true)
    private String password;
    @Column(scale = 1000, nullable = true)
    private long sortNo;
    @Column(nullable = false, length = 1)
    private String status;

    @OneToOne
    @JoinColumn(name = "person_id")
    private Person person;

    @Column(length = 20)
    private String location;

    @ManyToMany
    @JoinTable(
            name = "t_user_role",
            joinColumns = {@JoinColumn(name = "user_id")},
            inverseJoinColumns = {@JoinColumn(name = "role_id")
            }
    )
    private Set<Role> roleList;


}

