/*
 * Copyright (C) 2012 LG CNS Inc.
 * All right reserved
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포 사용하는 행위를 금지합니다.
 *
 * @version $id$
 */
package com.lgcns.smartwcs.multi_language.code.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
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
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class MultiLangCode {


    @Schema(name = "Language Code")
    private String langCd;

    @Schema(name = "Common Header Code")
    private String comHdrCd;

    @Schema(name = "Common Detail Code")
    private String comDtlCd;

    @Schema(name = "Common Detail Name")
    private String comDtlNm;

    @Schema(name = "Common Detail Language Name")
    private String comDtlLangNm;

    @Schema(name = "Common Header Name")
    private String comHdrNm;

    private String fileName;

    /**
     * 메뉴 아이디
     */
    @Schema(name = "등록자 아이디")
    private String regId;

    /**
     * 등록일시
     */
    @Schema(name = "등록일시")
    @CreationTimestamp
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime regDt;


}
