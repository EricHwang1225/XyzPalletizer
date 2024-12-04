package com.lgcns.smartwcs.cst.service;

import com.google.common.collect.Lists;
import com.lgcns.smartwcs.common.model.CommonExcelModel;
import com.lgcns.smartwcs.cst.model.Cst;
import com.lgcns.smartwcs.cst.model.CstSearchCondition;
import com.lgcns.smartwcs.cst.repository.CstRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CstExcelService {
    /**
     * Cst Repository 객체
     */
    @Autowired
    private CstRepository cstRepository;

    public List<CommonExcelModel> getCstExcel(CstSearchCondition condition) {

        List<CommonExcelModel> cstExcelSheetList = new ArrayList<>();
        //sheet 1개의 데이터
        CommonExcelModel excelSheetModel = new CommonExcelModel();

        String fileName = condition.getFileName();

        if(fileName.equals("cstList")) {

            excelSheetModel.setFileName(fileName);
            // sheet가 여러개일 경우 시트 이름을 지정해야한다.
            excelSheetModel.setSheetName("sheet1");

            // 표의 타이틀을 정의.
            String[] titleArray = {"화주 코드", "화주 명", "사용여부"};
            excelSheetModel.setTitleArray(titleArray);

            List<Cst> cstList = Lists.newArrayList(cstRepository.findAllBySearch(condition));
            List<List<Object>> rowObjectList = new ArrayList<>();
            List<Object> colObjectList = null;

            // row 값을 순서대로 리스트에 넣는 일.
            for (Cst cst : cstList) {
                // columnObject를 생성.
                colObjectList = new ArrayList<>();
                colObjectList.add(cst.getCstCd());
                colObjectList.add(cst.getCstNm());
                colObjectList.add(cst.getUseYn());

                // column Object 를 row에 추가.
                rowObjectList.add(colObjectList);
            }

            excelSheetModel.setDataObjectList(rowObjectList);

            // 1개 sheet를 추가. commonExcelModel을 여러개 추가하면 sheet도 여러개가 생성됨.
            cstExcelSheetList.add(excelSheetModel);

        }
        return cstExcelSheetList;
    }
}
