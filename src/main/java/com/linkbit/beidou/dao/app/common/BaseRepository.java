package com.linkbit.beidou.dao.app.common;/**
 * Created by Administrator on 2016/11/8.
 */

import lombok.Data;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 基础DAO
 *
 * @author
 * @create 2016-11-08 15:46
 **/
@Repository
public abstract interface BaseRepository extends CrudRepository, PagingAndSortingRepository {




}
