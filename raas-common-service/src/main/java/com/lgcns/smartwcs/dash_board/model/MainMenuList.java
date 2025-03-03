/*
 * Copyright (C) 2012 LG CNS Inc.
 * All right reserved
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포 사용하는 행위를 금지합니다.
 *
 * @version $id$
 */
package com.lgcns.smartwcs.dash_board.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * <PRE>
 * Rept Master Entity 클래스
 * </PRE>
 *
 * @author $Author$
 * @version $Id$
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MainMenuList {



    /**
     * Company 코드
     */
    @JsonProperty("coCd")
    @Schema(description = "Company 코드")
    private String coCd;

    /**
     * 센터 코드
     */
    @JsonProperty("cntrCd")
    @Schema(description = "센터 코드")
    private String cntrCd;

    /**
     * role 코드
     */
    @Schema(description = "설비 코드")
    private String roleCd;

    /**
     * language 코드
     */
    @Schema(description = "language 코드")
    private String langCd;

}
