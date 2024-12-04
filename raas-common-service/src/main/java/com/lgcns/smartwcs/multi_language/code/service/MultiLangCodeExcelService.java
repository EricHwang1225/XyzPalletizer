package com.lgcns.smartwcs.multi_language.code.service;

import com.lgcns.smartwcs.common.model.CommonExcelModel;
import com.lgcns.smartwcs.multi_language.code.model.MultiLangCode;
import com.lgcns.smartwcs.multi_language.code.repository.MultiLangCodeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MultiLangCodeExcelService {
    /**
     * MultiLangCode Repository 객체
     */
    @Autowired
    private MultiLangCodeMapper multiLangCodeMapper;

    public List<CommonExcelModel> getMultiLangCodeExcel(MultiLangCode condition) {

        List<CommonExcelModel> multiLangCodeExcelSheetList = new ArrayList<>();
        //sheet 1개의 데이터
        CommonExcelModel excelSheetModel = new CommonExcelModel();

        String fileName = condition.getFileName();

        if (fileName.equals("multiLangCodeList")) {

            excelSheetModel.setFileName(fileName);
            // sheet가 여러개일 경우 시트 이름을 지정해야한다.
            excelSheetModel.setSheetName("sheet1");

            // 표의 타이틀을 정의.
            String[] titleArray = {"대분류 코드", "대분류 코드 명", "상세 코드", "상세 코드 명", "다국어 코드", "상세코드 다국어명"};
            excelSheetModel.setTitleArray(titleArray);

            List<MultiLangCode> codeList = multiLangCodeMapper.getMultiLangCodeExcelList(condition);
            List<List<Object>> rowObjectList = new ArrayList<>();
            List<Object> colObjectList = null;

            // row 값을 순서대로 리스트에 넣는 일.
            for (MultiLangCode code : codeList) {
                // columnObject를 생성.
                colObjectList = new ArrayList<>();
                colObjectList.add(code.getComHdrCd());
                colObjectList.add(code.getComHdrNm());
                colObjectList.add(code.getComDtlCd());
                colObjectList.add(code.getComDtlNm());
                colObjectList.add(condition.getLangCd());
                colObjectList.add(code.getComDtlLangNm());

                // column Object 를 row에 추가.
                rowObjectList.add(colObjectList);
            }

            excelSheetModel.setDataObjectList(rowObjectList);

            // 1개 sheet를 추가. commonExcelModel을 여러개 추가하면 sheet도 여러개가 생성됨.
            multiLangCodeExcelSheetList.add(excelSheetModel);

        }
        return multiLangCodeExcelSheetList;
    }
}
