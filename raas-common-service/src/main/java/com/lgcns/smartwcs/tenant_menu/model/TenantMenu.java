/*
 * Copyright (C) 2012 LG CNS Inc.
 * All right reserved
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포 사용하는 행위를 금지합니다.
 *
 * @version $id$
 */
package com.lgcns.smartwcs.tenant_menu.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

/**
 * <PRE>
 * Tenant Entity 클래스.
 * </PRE>
 *
 * @author $Author$
 * @version $Id$
 */

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class TenantMenu {

    @Schema(name = "회사 코드(Tenant Code)")
    private String coCd;

    @Schema(name = "Menu ID")
    private String menuId;

    @Schema(name = "Menu Name")
    private String menuNm;

    @Schema(name = "사용 유무")
    private String useYn;

    @Schema(name = "등록자 아이디")
    private String regId;

    @Schema(name = "등록일시")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime regDt;
}
