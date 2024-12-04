package com.lgcns.smartwcs.wms_url.service;

import com.google.common.collect.Lists;
import com.lgcns.smartwcs.common.model.CommonExcelModel;
import com.lgcns.smartwcs.wms_url.model.WmsUrlSearchCondition;
import com.lgcns.smartwcs.wms_url.repository.WmsUrlMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class WmsUrlExcelService {

    @Autowired
    private WmsUrlMapper wmsUrlMapper;

    public List<CommonExcelModel> getWmsUrlExcel(WmsUrlSearchCondition condition) {

        List<CommonExcelModel> wmsUrlExcelSheetList = new ArrayList<>();
        //sheet 1개의 데이터
        CommonExcelModel excelSheetModel = new CommonExcelModel();

        String fileName = condition.getFileName();

        if(fileName.equals("wmsUrlList")) {

            excelSheetModel.setFileName(fileName);
            // sheet가 여러개일 경우 시트 이름을 지정해야한다.
            excelSheetModel.setSheetName("WMS Url List");

            // 표의 타이틀을 정의.
            String[] titleArray = {"Tenant ID", "Tenant 명", "인터페이스 ID", "인터페이스 명", "인터페이스 주소" };
            excelSheetModel.setTitleArray(titleArray);

            List<Map<String, Object>> wmsUrlList = Lists.newArrayList(wmsUrlMapper.getWmsUrlExcelList(condition));


            List<List<Object>> rowObjectList = new ArrayList<>();
            List<Object> colObjectList = null;

            // row 값을 순서대로 리스트에 넣는 일.
            for (Map<String, Object> wmsUrl : wmsUrlList) {
                // columnObject를 생성.
                colObjectList = new ArrayList<>();
                colObjectList.add(wmsUrl.get("coCd"));
                colObjectList.add(wmsUrl.get("coNm"));
                colObjectList.add(wmsUrl.get("wmsIfId"));
                colObjectList.add(wmsUrl.get("wmsIfNm"));
                colObjectList.add(wmsUrl.get("wmsIfUrl"));


                // column Object 를 row에 추가.
                rowObjectList.add(colObjectList);
            }

            excelSheetModel.setDataObjectList(rowObjectList);

            // 1개 sheet를 추가. commonExcelModel을 여러개 추가하면 sheet도 여러개가 생성됨.
            wmsUrlExcelSheetList.add(excelSheetModel);

        }
        return wmsUrlExcelSheetList;
    }
}
