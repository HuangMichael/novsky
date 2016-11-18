package com.linkbit.beidou.service.app;

import com.linkbit.beidou.domain.app.MyPage;
import com.linkbit.beidou.utils.export.docType.ExcelDoc;
import com.linkbit.beidou.utils.export.exporter.DataExport;
import com.linkbit.beidou.utils.export.exporter.ExcelDataExporter;
import lombok.Data;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * Created by huangbin  on 2016/5/20.
 * 日志对象
 */
@Service
@Data
public class BaseService {


    protected Log log = LogFactory.getLog(this.getClass());

    protected List dataList;

    /**
     * @param request
     * @param response
     * @param docName
     * @param titles
     * @param colNames
     */
    public void exportExcel(HttpServletRequest request, HttpServletResponse response, String docName, String[] titles, String[] colNames) {
        DataExport dataExport = new ExcelDataExporter();
        try {
            dataExport.export(new ExcelDoc(), request, response, titles, colNames, this.getDataList(), docName);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * @param searchPhrase
     * @param pageable
     * @return
     */
    public Page findByConditions(String searchPhrase, Pageable pageable) {

        return null;
    }
}
