package com.subway.utils.importer.tool;

import com.subway.service.app.BaseService;

import java.io.IOException;
import java.util.List;

/**
 * Created by Administrator on 2016/12/5.
 * 文档导入接口
 */
public interface Importable {


    /**
     * @param baseService
     * @param list
     */
    void importData(BaseService baseService, List list);


    /**
     * @param filePath
     * @param className
     * @param columns
     * @return
     */
    List assembleList(String filePath, String className, List<String> columns)  throws IOException;
}
