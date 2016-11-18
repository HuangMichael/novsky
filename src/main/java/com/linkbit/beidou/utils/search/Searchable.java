package com.linkbit.beidou.utils.search;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Created by huangbin on 2016/11/18.
 * <p>
 * 复合条件查询接口
 */
public interface Searchable {


    /**
     * @param searchPhrase
     * @param paramSize
     * @param pageable
     * @return
     */
    Page findByConditions(String searchPhrase, int paramSize, Pageable pageable);


    /**
     * @param searchPhrase
     * @param paramSize
     * @return
     */
    List findByConditions(String searchPhrase, int paramSize);

}
