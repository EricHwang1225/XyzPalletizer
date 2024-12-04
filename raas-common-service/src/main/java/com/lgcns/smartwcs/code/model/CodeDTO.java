/*
 * Copyright (C) 2012 LG CNS Inc.
 * All right reserved
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포 사용하는 행위를 금지합니다.
 *
 * @version $id$
 */
package com.lgcns.smartwcs.code.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * <PRE>
 * 공통 코드 Entity 클래스.
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
public class CodeDTO {

    @Schema(example = "회사 코드")
    private String coCd;

    @Schema(example = "대분류 코드")
    private String comHdrCd;

    @Schema(example = "상세 분류 코드")
    private String comDtlCd;

    @Schema(example = "대분류 이름")
    private String comHdrNm;

    @Schema(example = "상세 분류 이름")
    private String comDtlNm;

    @Schema(example = "사용 여부")
    private String useYn;

    @Schema(example = "대분류 플래그")
    private String hdrFlag;

    @Schema(name = "정렬 번호", example = "2")
    private Integer sortSeq;

    @Schema(name = "등록자 아이디")
    private String regId;

    @Schema(name = "수정자 아이디")
    private String updId;

    @Schema(name = "등록일시")
    private String regDt;

    @Schema(name = "수정일시")
    private String updDt;

    public Code dtoMapping() {

        return Code.builder()
                .coCd(this.getCoCd())
                .comHdrCd(this.getComHdrCd())
                .comDtlCd(this.getComDtlCd())
                .comHdrNm(this.getComHdrNm())
                .comDtlNm(this.getComDtlNm())
                .useYn(this.getUseYn())
                .hdrFlag(this.getHdrFlag())
                .sortSeq(this.getSortSeq())
                .regId(this.getRegId())
                .updId(this.getUpdId())
                .build();
    }
}
