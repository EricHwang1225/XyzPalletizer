/*
 * Copyright (C) 2012 LG CNS Inc.
 * All right reserved
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포 사용하는 행위를 금지합니다.
 *
 * @version $id$
 */
package com.lgcns.smartwcs.code.repository;

import com.lgcns.smartwcs.code.model.Code;
import com.lgcns.smartwcs.code.model.CodeSearchCondition;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <PRE>
 * Menu Mapper 클래스
 * </PRE>
 *
 * @author $Author$
 * @version $Id$
 */
@Repository
@Mapper
public interface CodeMapper {

    List<Code> getCodeHdrList(CodeSearchCondition codeSearchCondition);

    List<Code> getCodeLangList(CodeSearchCondition codeSearchCondition);
}
