/*
 * Copyright (C) 2012 LG CNS Inc.
 * All right reserved
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포 사용하는 행위를 금지합니다.
 *
 * @version $id$
 */
package com.lgcns.smartwcs.common.utils;

import com.lgcns.smartwcs.common.model.CommonExcelModel;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.servlet.view.AbstractView;

import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

public class ExcelView extends AbstractView {

    /** The content type for an Excel response */
    private static final String CONTENT_TYPE_XLSX = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";

    @Override
    protected boolean generatesDownloadContent() {
        return true;
    }

    /**
     * Renders the Excel view, given the specified model.
     */
    @Override
    protected final void renderMergedOutputModel(
            Map<String, Object> model
            , HttpServletRequest request
            , HttpServletResponse response) throws Exception {

        Workbook workbook = createWorkbook();

        setContentType(CONTENT_TYPE_XLSX);

        List<CommonExcelModel> modelList = (List<CommonExcelModel>) model.get("modelList");

        buildExcelDocument(modelList, workbook, request, response);

        // Set the content type.
        response.setContentType(getContentType());

        // Flush byte array to servlet output stream.
        ServletOutputStream out = response.getOutputStream();
        out.flush();
        workbook.write(out);
        out.flush();
        if (workbook instanceof SXSSFWorkbook) {
            ((SXSSFWorkbook) workbook).dispose();
        }

        // Don't close the stream - we didn't open it, so let the container
        // handle it.
    }

    protected Workbook createWorkbook() {
        return new XSSFWorkbook();
    }

    @SuppressWarnings("unchecked")
    protected void buildExcelDocument(
            List<CommonExcelModel> modelList
            , Workbook workbook
            , HttpServletRequest request
            , HttpServletResponse response)  {
        Map<String, Object> model = null;

        for(CommonExcelModel commonExcelModel: modelList) {

            String fileName = commonExcelModel.getFileName();
            logger.trace(request.toString() + response.toString() + fileName );
            String sheetName = commonExcelModel.getSheetName();
            String[] titleArray = commonExcelModel.getTitleArray();
            List<List<Object>> dataObjectList = commonExcelModel.getDataObjectList();

            //Sheet 생성
            Sheet sheet = workbook.createSheet(sheetName);
            Row row = null;

            CellStyle titleCellStyle = workbook.createCellStyle();
            titleCellStyle.setBorderRight(BorderStyle.THIN);
            titleCellStyle.setRightBorderColor(IndexedColors.BLACK.getIndex());
            titleCellStyle.setBorderTop(BorderStyle.THIN);
            titleCellStyle.setTopBorderColor(IndexedColors.BLACK.getIndex());
            titleCellStyle.setBorderLeft(BorderStyle.THIN);
            titleCellStyle.setLeftBorderColor(IndexedColors.BLACK.getIndex());
            titleCellStyle.setBorderBottom(BorderStyle.THIN);
            titleCellStyle.setBottomBorderColor(IndexedColors.BLACK.getIndex());

            CellStyle dataCellStyle = workbook.createCellStyle();
            dataCellStyle.setBorderRight(BorderStyle.THIN);
            dataCellStyle.setRightBorderColor(IndexedColors.BLACK.getIndex());
            dataCellStyle.setBorderTop(BorderStyle.THIN);
            dataCellStyle.setTopBorderColor(IndexedColors.BLACK.getIndex());
            dataCellStyle.setBorderLeft(BorderStyle.THIN);
            dataCellStyle.setLeftBorderColor(IndexedColors.BLACK.getIndex());
            dataCellStyle.setBorderBottom(BorderStyle.THIN);
            dataCellStyle.setBottomBorderColor(IndexedColors.BLACK.getIndex());

            int rowCount = 0;
            int cellCount = 0;

            row = sheet.createRow(rowCount); //0번 로우는 제목줄

            // 제목 Cell 생성
            for(String title: titleArray) {
                Cell cell = row.createCell(cellCount++);
                cell.setCellStyle(titleCellStyle);
                cell.setCellValue(title);
            }

            //rowCount = 1; //rowCount++ 로 처리가능
            rowCount++;
            for(List<Object> rawDataObject: dataObjectList) {
                row = sheet.createRow(rowCount++); // 1번째 로우를 생성

                cellCount=0; // cell 시작은 0부터.
                for(Object cellObject: rawDataObject) {

                    if(cellObject != null) { //데이터가 널임을 확인
                        Cell cell = row.createCell(cellCount);
                        cell.setCellStyle(dataCellStyle);
                        cell.setCellValue(cellObject.toString()); //데이터를 가져와 입력
                    } else {
                        Cell cell = row.createCell(cellCount);
                        cell.setCellStyle(dataCellStyle);
                        cell.setCellValue("");
                    }
                    cellCount++; //다음 셀로 이동 +1
                }
            }
        }
    }
}
