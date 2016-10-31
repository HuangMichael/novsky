package com.linkbit.beidou.utils;

import com.linkbit.beidou.domain.equipments.Equipments;
import com.linkbit.beidou.domain.equipments.Vequipments;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.VerticalAlignment;
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

import static javafx.scene.input.KeyCode.H;

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
    public static void exportExcel(HttpServletRequest request, HttpServletResponse resp, List<Vequipments> equipmentsList, String[] titles) throws UnsupportedEncodingException {
        HSSFWorkbook wb = new HSSFWorkbook();
        request.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");
        resp.setContentType("application/x-download");
        String fileName = "设备信息.xls";
        fileName = URLEncoder.encode(fileName, "UTF-8");
        resp.addHeader("Content-Disposition", "attachment;filename=" + fileName);
        HSSFSheet sheet = wb.createSheet("设备信息");
        sheet.setDefaultRowHeight((short) (256));
        sheet.setDefaultColumnWidth((short) (20));
        sheet.setFitToPage(true);
        //sheet.setColumnWidth(0, 50 * 160);
        HSSFFont font = wb.createFont();
        font.setFontName("宋体");
        font.setFontHeightInPoints((short) 16);
        HSSFRow row = sheet.createRow(0);
        sheet.createRow(1);
        sheet.createRow(2);
        sheet.createRow(3);
        sheet.createRow(4);
        HSSFCellStyle style = wb.createCellStyle();
        style.setVerticalAlignment(VerticalAlignment.CENTER);
        style.setAlignment(HorizontalAlignment.LEFT);

        for (int i = 0; i < titles.length; i++) {
            HSSFCell cell = row.createCell(i);
            cell.setCellValue(titles[i]);
            cell.setCellStyle(style);
        }
        for (int i = 0; i < equipmentsList.size(); i++) {
            HSSFRow row1 = sheet.createRow(i + 1);
            row1.setRowStyle(style);
            Vequipments equipments = equipmentsList.get(i);
            row1.createCell(0).setCellValue(i + 1);
            row1.createCell(1).setCellValue(equipments.getEqCode());//设备编号
            row1.createCell(2).setCellValue(equipments.getEqName());//设备名称
            row1.createCell(3).setCellValue(equipments.getEqClass());//设备分类
            row1.createCell(4).setCellValue(equipments.getLocName());//设备位置
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

