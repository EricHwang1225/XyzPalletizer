/*
 * Copyright (C) 2012 LG CNS Inc.
 * All right reserved
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포 사용하는 행위를 금지합니다.
 *
 * @version $id$
 */
package com.lgcns.smartwcs.multi_language.code.repository;

import com.lgcns.smartwcs.multi_language.code.model.MultiLangCode;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <PRE>
 * MultiLang Mapper 클래스
 * </PRE>
 *
 * @author $Author$
 * @version $Id$
 */
@Repository
@Mapper
public interface MultiLangCodeMapper {

    List<MultiLangCode> getMultiLangCodeList(MultiLangCode multiLangCode);

    List<MultiLangCode> getMultiLangCodeExcelList(MultiLangCode multiLangCode);

    void saveMultiLangCode(MultiLangCode multiLangCode);
}
