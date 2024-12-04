/*
 * Copyright (C) 2012 LG CNS Inc.
 * All right reserved
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포 사용하는 행위를 금지합니다.
 *
 * @version $id$
 */
package com.lgcns.smartwcs.menu.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.lgcns.smartwcs.common.model.BaseDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * <PRE>
 * Menu Entity 클래스.
 * </PRE>
 *
 * @author $Author$
 * @version $Id$
 */

@Data
@EqualsAndHashCode(callSuper = false)
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class MenuDTO extends BaseDTO {

    @Schema(example = "메뉴 아이디")
    private String menuId;

    @Schema(example = "메뉴 이름")
    private String menuNm;

    @Schema(example = "메뉴 설명")
    private String menuDesc;

    @Schema(example = "1")
    private Integer menuLvl;

    @Schema(example = "메뉴 종류(팝업, 탭, 연계설비, SCADA")
    private String menuType;

    @Schema(example = "메뉴 소스")
    private String menuUrl;

    @Schema(example = "메뉴 아이콘 이미지")
    private String menuIcon;

    @Schema(example = "트리 리스트 아이디")
    private String treeId;

    @Schema(example = "트리 부모 아이디")
    private String parentId;

    @Schema(example = "0")
    private Integer sortSeq;

    @Schema(example = "메인 화면 Dashboard Icon 이미지")
    private String mainShowIcon;

    @Schema(example = "0")
    private Integer userLvl;

    @Schema(example = "사용 여부")
    private String useYn;

    public Menu dtoMapping() {

        return Menu.builder()
                .menuId(this.getMenuId())
                .menuNm(this.getMenuNm())
                .menuDesc(this.getMenuDesc())
                .menuLvl(this.getMenuLvl())
                .menuType(this.getMenuType())
                .menuUrl(this.getMenuUrl())
                .menuIcon(this.getMenuIcon())
                .treeId(this.getTreeId())
                .parentId(this.getParentId())
                .sortSeq(this.getSortSeq())
                .mainShowIcon(this.getMainShowIcon())
                .userLvl(this.getUserLvl())
                .useYn(this.getUseYn())
                .regId(this.getRegId())
                .updId(this.getUpdId())
                .build();
    }
}
