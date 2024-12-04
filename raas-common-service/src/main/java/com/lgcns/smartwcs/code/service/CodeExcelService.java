package com.lgcns.smartwcs.code.service;

import com.google.common.collect.Lists;
import com.lgcns.smartwcs.code.model.Code;
import com.lgcns.smartwcs.code.model.CodeSearchCondition;
import com.lgcns.smartwcs.code.repository.CodeMapper;
import com.lgcns.smartwcs.code.repository.CodeRepository;
import com.lgcns.smartwcs.common.model.CommonExcelModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CodeExcelService {

    /**
     * User Repository 객체
     */
    @Autowired
    private CodeRepository codeRepository;

    @Autowired
    private CodeMapper codeMapper;

    public List<CommonExcelModel> getAllCodeExcel(CodeSearchCondition condition) {

        List<CommonExcelModel> codeExcelSheetList = new ArrayList<>();
        //sheet 1개의 데이터
        CommonExcelModel headerExcelSheetModel = null;
        CommonExcelModel detailExcelSheetModel = null;

        String fileName = condition.getFileName();

        if(fileName.equals("codeList")) {
            headerExcelSheetModel = new CommonExcelModel();
            headerExcelSheetModel.setFileName(fileName);
            // sheet가 여러개일 경우 시트 이름을 지정해야한다.
            headerExcelSheetModel.setSheetName("code");

            // 표의 타이틀을 정의.
            String[] headerTitleArray = {"코드 대분류","코드 대분류 명", "사용 여부", "정렬 순서"};
            headerExcelSheetModel.setTitleArray(headerTitleArray);

            condition.setHdrFlag("Y");
            List<Code> codeList = Lists.newArrayList(codeMapper.getCodeHdrList(condition));

            List<List<Object>> rowObjectList = new ArrayList<>();
            List<Object> colObjectList = null;

            // row 값을 순서대로 리스트에 넣는 일.
            for (Code code : codeList) {
                // columnObject를 생성.
                colObjectList = new ArrayList<>();
                colObjectList.add(code.getComHdrCd());
                colObjectList.add(code.getComHdrNm());
                colObjectList.add(code.getUseYn());
                colObjectList.add(code.getSortSeq());

                // column Object 를 row에 추가.
                rowObjectList.add(colObjectList);

            }

            headerExcelSheetModel.setDataObjectList(rowObjectList);

            // 1개 sheet를 추가. commonExcelModel을 여러개 추가하면 sheet도 여러개가 생성됨.
            codeExcelSheetList.add(headerExcelSheetModel);


            // 2 번째 시트 생성 ------------------------------------------------------------------
            detailExcelSheetModel = new CommonExcelModel();
            detailExcelSheetModel.setSheetName("codeDetail");

            // 표의 타이틀을 정의.
            String[] detailTitleArray = {"상세 코드","상세 코드 명","사용 여부", "정렬 순서"};
            detailExcelSheetModel.setTitleArray(detailTitleArray);

            condition.setComHdrCd(condition.getSelectedComHdrCd());
            condition.setHdrFlag("N");
            condition.setCoCd("LGWCS"); // LGWCS 하드 코딩
            List<Code> codeDetailList = Lists.newArrayList(codeRepository.findAllBySearch(condition));
            rowObjectList = new ArrayList<>();

            // row 값을 순서대로 리스트에 넣는 일.
            for (Code codeDetail : codeDetailList) {

                // columnObject를 생성.
                colObjectList = new ArrayList<>();
                colObjectList.add(codeDetail.getComDtlCd());
                colObjectList.add(codeDetail.getComDtlNm());
                colObjectList.add(codeDetail.getUseYn());
                colObjectList.add(codeDetail.getSortSeq());

                // column Object 를 row에 추가.
                rowObjectList.add(colObjectList);
            }

            detailExcelSheetModel.setDataObjectList(rowObjectList);

            // 2번째 시트를 추가.
            codeExcelSheetList.add(detailExcelSheetModel);
        }

        return codeExcelSheetList;
    }
}
