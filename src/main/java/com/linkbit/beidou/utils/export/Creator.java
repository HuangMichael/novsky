package com.linkbit.beidou.utils.export;/**
 * Created by Administrator on 2016/11/1.
 */

/**
 * 导出创建者
 *
 * @author
 * @create 2016-11-01 11:18
 **/
public abstract class Creator {

    public abstract <T extends DocProduct> T createProduct(Class<T> c);

}
