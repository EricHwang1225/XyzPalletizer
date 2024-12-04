/*
 * Copyright (C) 2012 LG CNS Inc.
 * All right reserved
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포 사용하는 행위를 금지합니다.
 *
 * @version $id$
 */
package com.lgcns.smartwcs.role.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
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
 * Role Menu Return 클래스
 * </PRE>
 *
 * @author $Author$
 * @version $Id$
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RoleMenu {

    /**
     * 회사코드
     */
    @Schema(example = "회사 코드")
    private String coCd;

    /**
     * Center Code
     */
    @Schema(example = "Center Code")
    private String cntrCd;

    /**
     * Role Code
     *
     */
    @Schema(example = "Role Code")
    private String roleCd;

    /**
     * 메뉴 아이디
     */
    @Schema(example = "메뉴 아이디")
    private String menuId;


    /**
     * Role Menu Code
     * No access - N
     * Read only - R
     * R/W - A
     */
    @Schema(example = "Role Menu Code")
    private String roleMenuCd;

    @Schema(example = "Main Screen Visible")
    private String mainShowYn;

    /**
     * 메뉴 이름
     */
    @Schema(example = "메뉴 이름")
    private String menuNm;

    /**
     * 메뉴 레벨
     */
    @JsonIgnore
    @Schema(example = "1")
    private Integer menuLvl;

    /**
     * 트리 리스트 아이디
     */
    @JsonIgnore
    @Schema(example = "트리 리스트 아이디")
    private String treeId;

    /**
     * 트리 부모 아이디
     */
    @JsonIgnore
    @Schema(example = "트리 부모 아이디")
    private String parentId;

    /**
     * 등록자 아이디
     */
    @Schema(example = "등록자 아이디")
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
