package com.subway.domain.app.sysLog;

/**
 * Created by Administrator on 2016/12/6.
 */

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

/**
 * 系统日志表
 *
 * @author
 * @create 2016-12-06 9:54
 **/
@Entity
@Table(name = "T_SYS_LOG")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SysLog {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String remoteIp;
    private String url;
    private String userName;
    private Date requestTime;
}
