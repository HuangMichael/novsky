package com.linkbit.beidou.utils;

import com.linkbit.beidou.domain.equipments.Vequipments;
import com.linkbit.beidou.domain.workOrder.VworkOrderReportBill;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.hibernate.service.spi.ServiceException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Method;
import java.net.URLEncoder;
import java.util.List;

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
     * @param equipmentsList
     * @param titles
     * @param colNames
     * @param docName
     * @throws UnsupportedEncodingException
     */
    public static void exportExcel(HttpServletRequest request, HttpServletResponse resp, List<Vequipments> equipmentsList, List<String> titles, List<String> colNames, String docName) throws UnsupportedEncodingException {
        HSSFWorkbook wb = new HSSFWorkbook();
        request.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");
        resp.setContentType("application/x-download");
        String fileName = docName + ".xls";
        fileName = URLEncoder.encode(fileName, "UTF-8");
        resp.addHeader("Content-Disposition", "attachment;filename=" + fileName);
        HSSFSheet sheet = wb.createSheet(docName);
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

        for (int i = 0; i < titles.size(); i++) {
            if (titles.get(i) != null) {
                HSSFCell cell = row.createCell(i);
                cell.setCellValue(titles.get(i).toString());
                cell.setCellStyle(style);
            }

        }
        Method method = null;
        for (int i = 0; i < equipmentsList.size(); i++) {
            HSSFRow row1 = sheet.createRow(i + 1);
            row1.setRowStyle(style);
            Vequipments equipments = equipmentsList.get(i);
            for (int j = 0; j < colNames.size(); j++) {
                if (j > 0 && colNames.get(j) != null) {

                    System.out.println();
                    try {
                        method = equipments.getClass().getMethod("get" + StringUtils.upperCaseCamel(colNames.get(j).toString()));
                        row1.createCell(j).setCellValue(method.invoke(equipments).toString());//设备编号
                    } catch (Exception e) {
                        e.printStackTrace();

                    }
                } else {
                    row1.createCell(j).setCellValue(i + 1);
                }

            }


        }
        try {
            OutputStream out = resp.getOutputStream();
            wb.write(out);
            out.close();
        } catch (
                ServiceException e)

        {
            System.out.println("=====导出excel异常====");
        } catch (
                Exception e1)

        {
            System.out.println("=====导出excel异常====");
        }
    }

    /**
     * excel导出交易记录
     *
     * @param request
     * @param resp
     * @param vworkOrderReportBillList
     * @param titles
     * @param colNames
     * @param docName
     * @throws UnsupportedEncodingException
     */
    public static void exportExcelReportCart(HttpServletRequest request, HttpServletResponse resp, List<VworkOrderReportBill> vworkOrderReportBillList, List<String> titles, List<String> colNames, String docName) throws UnsupportedEncodingException {
        HSSFWorkbook wb = new HSSFWorkbook();
        request.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");
        resp.setContentType("application/x-download");
        String fileName = docName + ".xls";
        fileName = URLEncoder.encode(fileName, "UTF-8");
        resp.addHeader("Content-Disposition", "attachment;filename=" + fileName);
        HSSFSheet sheet = wb.createSheet(docName);
        sheet.setDefaultRowHeight((short) (256));
        sheet.setDefaultColumnWidth((short) (20));
        sheet.setFitToPage(true);
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

        for (int i = 0; i < titles.size(); i++) {
            if (titles.get(i) != null) {
                HSSFCell cell = row.createCell(i);
                cell.setCellValue(titles.get(i).toString());
                cell.setCellStyle(style);
            }

        }
        Method method = null;
        for (int i = 0; i < vworkOrderReportBillList.size(); i++) {
            HSSFRow row1 = sheet.createRow(i + 1);
            row1.setRowStyle(style);
            VworkOrderReportBill vworkOrderReportBill = vworkOrderReportBillList.get(i);
            for (int j = 0; j < colNames.size(); j++) {
                if (j > 0 && colNames.get(j) != null) {
                    try {
                        method = vworkOrderReportBill.getClass().getMethod("get" + StringUtils.upperCaseCamel(colNames.get(j).toString()));
                        System.out.println("get"+StringUtils.upperCaseCamel(colNames.get(j)));
                        row1.createCell(j).setCellValue(method.invoke(vworkOrderReportBill).toString());//设备编号
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    row1.createCell(j).setCellValue(i + 1);
                }
            }
        }
        try {
            OutputStream out = resp.getOutputStream();
            wb.write(out);
            out.close();
        } catch (
                ServiceException e)

        {
            System.out.println("=====导出excel异常====");
        } catch (
                Exception e1)

        {
            System.out.println("=====导出excel异常====");
        }
    }
}

