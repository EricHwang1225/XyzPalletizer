/*
 * Copyright (C) 2012 LG CNS Inc.
 * All right reserved
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포 사용하는 행위를 금지합니다.
 *
 * @version $id$
 */
package com.lgcns.smartwcs.health_check;


import com.lgcns.smartwcs.common.model.CommonJsonResponse;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.lgcns.smartwcs.common.utils.CommonConstants.MessageEnum;

/**
 * <PRE>
 * Health Check 컨트롤러 클래스.
 * </PRE>
 *
 * @author $Author$
 * @version $Id$
 */

@RestController
@RequestMapping(value = "/api/healthCheck")
public class HealthCheckController {


    /**
     * Health Check
     *
     * @return 페이징된 Health Check
     */
    @GetMapping(produces = {"application/json", "application/xml"})
    @Operation(summary = "사용자 목록 조회", description = "사용자 목록을 조회한다.")
    public CommonJsonResponse healthCheck(CommonJsonResponse commonJsonResponse) {
        commonJsonResponse.setData("OK");
        commonJsonResponse.setResultCode(100);
        commonJsonResponse.setResultMessage(MessageEnum.SUCCESS.getValue());

        return commonJsonResponse;
    }

}
