package com.linkbit.beidou.service.workOrderMatCost;

import com.linkbit.beidou.dao.app.resource.ResourceRepository;
import com.linkbit.beidou.dao.macCost.MatCostRepository;
import com.linkbit.beidou.dao.macCost.WorkOrderMatCostRepository;
import com.linkbit.beidou.domain.matCost.MatCost;
import com.linkbit.beidou.domain.matCost.WorkOrderMatCost;
import com.linkbit.beidou.object.ReturnObject;
import com.linkbit.beidou.service.commonData.CommonDataService;
import com.linkbit.beidou.utils.DateUtils;
import com.linkbit.beidou.utils.SessionUtil;
import com.linkbit.beidou.utils.UploadUtil;
import jxl.Sheet;
import jxl.Workbook;
import org.apache.tomcat.util.http.fileupload.servlet.ServletFileUpload;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by huangbin
 * 2016年9月29日10:03:31
 * <p/>
 * 物料消耗查询业务类
 */
@Service
public class WorkOrderMatCostService {

    @Autowired
    public ResourceRepository resourceRepository;

    @Autowired
    public MatCostRepository matCostRepository;
    @Autowired
    CommonDataService commonDataService;
    @Autowired
    WorkOrderMatCostRepository workOrderMatCostRepository;


    /**
     * 查询所有数据资源
     */
    public Page<WorkOrderMatCost> findAll(Pageable pageable) {
        return workOrderMatCostRepository.findAll(pageable);
    }

    /**
     * @param searchPhase 查询关键字
     * @param pageable    分页
     * @return
     */
    public Page<WorkOrderMatCost> findByCondition(String searchPhase, Pageable pageable) {
        return workOrderMatCostRepository.findByOrderLineNoContainsOrMatNameContainsOrMatModelContains(searchPhase, searchPhase, searchPhase, pageable);
    }


    /**
     * 获取导入的数据
     *
     * @param file
     * @param request
     * @return 上传并且导入数据
     */
    @Transactional
    public ReturnObject upload(MultipartFile file, HttpServletRequest request) {
        String contextPath = SessionUtil.getContextPath(request);
        String realPath = "upload\\excel\\matcost\\工单物资消耗" + DateUtils.convertDate2Str(null, "yyyy-MM-dd-HH-mm-ss") + ".xls";
        String filePath = contextPath + realPath;
        UploadUtil.uploadFile(file, filePath);
        return importExcel(filePath);
    }

    /**
     * 获取导入的数据
     *
     * @param filePath 文件路径
     * @return 将数据保存到数据库并返回结果
     */
    @Transactional
    public ReturnObject importExcel(String filePath) {
        List<WorkOrderMatCost> workOrderMatCostList = readExcelData(filePath);
        for (WorkOrderMatCost workOrderMatCost : workOrderMatCostList) {
            workOrderMatCostRepository.save(workOrderMatCost);
        }
        return commonDataService.getReturnType(!workOrderMatCostList.isEmpty(), "工单物料消耗数据导入成功", "工单物料消耗数据导入失败");
    }


    /**
     * @param filePath 文件路径
     * @return 获取导入的数据
     */
    @Transactional
    public List<WorkOrderMatCost> readExcelData(String filePath) {
        List<WorkOrderMatCost> workOrderMatCostList = new ArrayList<WorkOrderMatCost>();
        try {
            InputStream is = new FileInputStream(filePath);
            jxl.Workbook rwb = Workbook.getWorkbook(is);
            Sheet rs = rwb.getSheet(0);
            int rowNum = rs.getRows();
            for (int r = 1; r < rowNum; r++) {
                WorkOrderMatCost workOrderMatCost = new WorkOrderMatCost();
                workOrderMatCost.setOrderLineNo(rs.getCell(1, r).getContents());
                workOrderMatCost.setMatName(rs.getCell(2, r).getContents());
                workOrderMatCost.setMatModel(rs.getCell(3, r).getContents());
                workOrderMatCost.setMatAmount(Long.parseLong(rs.getCell(4, r).getContents()));
                workOrderMatCost.setMatPrice(Double.parseDouble(rs.getCell(5, r).getContents()));
                workOrderMatCost.setDataType("0");
                workOrderMatCostList.add(workOrderMatCost);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return workOrderMatCostList;
    }
}
