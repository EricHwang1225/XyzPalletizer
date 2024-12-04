/*
 * Copyright (C) 2012 LG CNS Inc.
 * All right reserved
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포 사용하는 행위를 금지합니다.
 *
 * @version $id$
 */
package com.lgcns.smartwcs.common.repository;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * <PRE>
 * 재고실사 예정 Mapper 클래스
 * </PRE>
 *
 * @author $Author$
 * @version $Id$
 */
@Repository
@Mapper
public interface SequenceMapper {



    /**
     * 새로운 재고실사 오더 UID를 얻어온다.
     * @return
     */
    Integer getInbdUid();

    /**
     * 새로운 출고 오더 UID를 얻어온다.
     * @return
     */
    Integer getObndUid();

    /**
     * 새로운 재고실사 오더 UID를 얻어온다.
     * @return
     */
    Integer getSttkUid();

    /**
     * 새로운 Task UID를 얻어온다.
     * @return
     */
    Integer getTaskUid();

    /**
     * 새로운 Task Group ID를 얻어온다.
     * @return
     */
    Integer getTaskGroupId();

    /**
     * 새로운 Task ID를 얻어온다.
     * @return
     */
    Integer getTaskId();

    /**
     * 새로운 적치완료 UID를 얻어온다.
     * @return
     */
    Integer getPtwyUid();

    /**
     * 새로운 피킹완료 UID를 얻어온다.
     * @return
     */
    Integer getPickUid();


    Integer getWcsTrackingId();

    Integer getAbnormUid();
}
