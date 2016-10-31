package com.linkbit.beidou.utils;

import com.linkbit.beidou.domain.equipments.Equipments;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.CellStyle;
import org.hibernate.service.spi.ServiceException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by huangbin on 2016/2/22 0022.
 * 上传附件工具类
 */

public class ExportUtil {


    /**
     * excel导出交易记录
     *
     * @param request
     * @param resp
     * @throws UnsupportedEncodingException
     */
    public static void exportExcel(HttpServletRequest request, HttpServletResponse resp, List<Equipments> equipmentsList) throws UnsupportedEncodingException {
        HSSFWorkbook wb = new HSSFWorkbook();
        request.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");
        resp.setContentType("application/x-download");
        String fileName = "设备信息.xls";
        fileName = URLEncoder.encode(fileName, "UTF-8");
        resp.addHeader("Content-Disposition", "attachment;filename=" + fileName);
        HSSFSheet sheet = wb.createSheet("设备信息");
        sheet.setDefaultRowHeight((short) (256));
        sheet.setDefaultColumnWidth((short)(12));
        sheet.setFitToPage(true);
        //sheet.setColumnWidth(0, 50 * 160);
        HSSFFont font = wb.createFont();
        font.setFontName("宋体");
        font.setFontHeightInPoints((short) 16);
        HSSFRow row = sheet.createRow(0);
        sheet.createRow(1);
        sheet.createRow(2);
        sheet.createRow(3);
        HSSFCellStyle style = wb.createCellStyle();
        HSSFCell cell = row.createCell(0);
        cell.setCellValue("序号");
        cell.setCellStyle(style);
        cell = row.createCell(1);
        cell.setCellValue("编号");
        cell.setCellStyle(style);
        cell = row.createCell(2);
        cell.setCellStyle(style);
        cell.setCellValue("设备名称");
        for (int i = 0; i < equipmentsList.size(); i++) {
            HSSFRow row1 = sheet.createRow(i + 1);
            Equipments equipments = equipmentsList.get(i);
            row1.createCell(0).setCellValue(i + 1);
            row1.createCell(1).setCellValue(equipments.getEqCode());//订单号
            row1.createCell(2).setCellValue(equipments.getDescription());//会员姓名
        }
        try {
            OutputStream out = resp.getOutputStream();
            wb.write(out);
            out.close();
        } catch (ServiceException e) {
            System.out.println("=====导出excel异常====");
        } catch (Exception e1) {
            System.out.println("=====导出excel异常====");
        }
    }
}

