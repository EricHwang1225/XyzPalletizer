/*
 * Copyright (C) 2012 LG CNS Inc.
 * All right reserved
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포 사용하는 행위를 금지합니다.
 *
 * @version $id$
 */
package com.lgcns.smartwcs.menu_uri_role.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

/**
 * <PRE>
 * Menu 검색 조건 클래스.
 * </PRE>
 *
 * @author $Author$
 * @version $Id$
 */
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class MenuUriRoleSearchCondition {

    @Schema(example = "메뉴 아이디")
    private String menuId;

    @Schema(example = "메뉴 명")
    private String menuNm;

    @Schema(example = "호출 uri")
    private String uri;

    @Schema(example = "사용 여부")
    private String useYn;

    /**
     * Excel Download FileName
     */
    private String fileName;
    private String methodType;

}
