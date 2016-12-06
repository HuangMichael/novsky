package com.subway.dao.locations;

import com.subway.domain.locations.Locations;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Created by huangbin on 2016/3/15 0008.
 * 位置信息查询接口
 */

@CacheConfig(cacheNames = "locationsList")
public interface LocationsRepository extends CrudRepository<Locations, Long> {
    /**
     * 查询所有设备类别
     */
    @Cacheable(key = "#p0")
    @Query("SELECT l FROM Locations l")
    List<Locations> findAll();


    /**
     * @param location 位置编码
     * @return
     */
    @Query("SELECT  l.id,l.location, l.description, l.superior, l.parent FROM Locations l where l.location like :location  ")
    List<Object> findTree(@Param("location") String location);

    /**
     * 根据状态查询设备类别
     */
    List<Locations> findByStatus(String status);

    /**
     * 根据id查询
     */
    Locations findById(long id);


    /**
     * @param locations
     * @return 保存位置信息
     */

    @CachePut(key = "#p0.id")
    Locations save(Locations locations);

    /**
     * 根据父节点查询
     */
    @Query(value = "select l from Locations l where l.parent=:id order by l.location desc ")
    List<Locations> findByParentOrderByLocationDesc(@Param("id") Long id);

    List<Locations> findByParent(Long id);


    /**
     * @param location
     * @return
     */
    List<Locations> findByLocationStartingWith(String location);

    /**
     * @param location
     * @param status
     * @return
     */
    List<Locations> findByLocationStartingWithAndStatus(String location, String status);

    /**
     * @param level 级别  <
     * @return 查询节点级别小于level的
     */
    List<Locations> findByLocLevelLessThan(Long level);


    /**
     * 根据位置编码模糊查询
     */
    List<Locations> findByLocation(String location);


    /**
     * @param locations 删除位置
     */
    @Override
    void delete(Locations locations);
}
