package com.linkbit.beidou.utils.export.exporter;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * Created by Bill on 2016/11/2.
 * 数据导出接口
 */
public interface DataExport {
    /**
     * @param request
     * @param response
     * @param titles
     * @param colNames
     * @param dataList
     * @param docName
     * @throws Exception
     */
    void export(HttpServletRequest request, HttpServletResponse response, List titles, List colNames, List dataList, String docName) throws Exception;
}
