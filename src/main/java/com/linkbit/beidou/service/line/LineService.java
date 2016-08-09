package com.linkbit.beidou.service.line;

import com.linkbit.beidou.dao.line.LineRepository;
import com.linkbit.beidou.domain.line.Line;
import com.linkbit.beidou.service.app.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * Created by huangbin on 2016/3/24.
 * 线路业务类
 */
@Service
public class LineService extends BaseService {

    @Autowired
    LineRepository lineRepository;

    /**
     * 根据状态查询所有启用的线路
     */
    public List<Line> findByStatus(String status) {
        status = StringUtils.isEmpty(status) ? "1" : status;
        return lineRepository.findByStatus(status);
    }

    /**
     * 根据id查询线路
     */
    public Line findById(Long id) {

        Line line = null;

        if (id != null) {
            line = lineRepository.findById(id);
        }
        return line;

    }
}
