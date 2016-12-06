package com.subway.utils.importer.tool;/**
 * Created by Administrator on 2016/12/5.
 */

import com.subway.service.app.BaseService;
import jxl.Sheet;
import jxl.Workbook;
import org.springframework.stereotype.Service;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * 导入实现业务类
 *
 * @author
 * @create 2016-12-05 9:34
 **/
@Service
public class ImportService implements Importable {


    /**
     * @param baseService
     * @param list
     */
    @Override
    public void importData(BaseService baseService, List list) {
        baseService.save(list);
    }


    /**
     * @param filePath
     * @param className
     * @param columns   导入列
     * @return
     */
    public List assembleList(String filePath, String className, List<String> columns)  throws IOException {
        List dataList = new ArrayList();
        try {
            InputStream is = new FileInputStream(filePath);
            jxl.Workbook rwb = Workbook.getWorkbook(is);
            Sheet rs = rwb.getSheet(0);
            int rowNum = rs.getRows();
            Method method;
            Object object = null;

            //从第一行开始  跳过标题行
            for (int r = 1; r < rowNum; r++) {
                object = Class.forName(className).newInstance();
                for (int c = 0; c < columns.size(); c++) {
                    method = object.getClass().getMethod("set" + columns.get(c), String.class);
                    method.invoke(object, rs.getCell(c, r).getContents());
                }
                dataList.add(object);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dataList;
    }
}
