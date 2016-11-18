package com.linkbit.beidou.utils;/**
 * Created by Administrator on 2016/9/2.
 */

import com.linkbit.beidou.domain.app.MyPage;
import com.linkbit.beidou.service.app.BaseService;
import com.linkbit.beidou.utils.search.Searchable;
import jdk.nashorn.internal.objects.annotations.Constructor;
import lombok.Data;
import org.omg.CORBA.Object;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

/**
 * 分页对象转换类
 *
 * @author
 * @create 2016-09-02 15:06
 **/
@Data
public class PageUtils {


   public  PageUtils() {

    }

    /**
     * @param searchPhrase
     * @param paramSize
     * @param current
     * @param rowCount
     * @return
     */
    public MyPage searchByService(Searchable searchable, String searchPhrase, int paramSize, int current, Long rowCount) {
        Page page = searchable.findByConditions(searchPhrase, paramSize, new PageRequest(current - 1, rowCount.intValue()));
        MyPage myPage = new MyPage();
        myPage.setRows(page.getContent());
        myPage.setRowCount(rowCount);
        myPage.setCurrent(current);
        myPage.setTotal(page.getTotalElements());
        return myPage;
    }

}
