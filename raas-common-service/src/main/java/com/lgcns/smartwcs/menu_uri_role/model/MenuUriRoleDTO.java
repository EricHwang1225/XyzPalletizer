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
public class MenuUriRoleDTO extends BaseDTO {

    @Schema(example = "UID KEY")
    private String methodUidKey;

    @Schema(example = "메뉴 아이디")
    private String menuId;

    @Schema(example = "Mehtod TYpe (GET, POST, PUT, DELETE")
    private String methodType;

    @Schema(example = "호출 uri")
    private String uri;

    @Schema(example = "uri 설명")
    private String uriDesc;

    @Schema(example = "사용 여부")
    private String useYn;

    public MenuUriRole dtoMapping() {

        return MenuUriRole.builder()
                .methodUidKey(this.getMethodUidKey())
                .menuId(this.getMenuId())
                .methodType(this.getMethodType())
                .uri(this.getUri())
                .uriDesc(this.getUriDesc())
                .useYn(this.getUseYn())
                .regId(this.getRegId())
                .updId(this.getUpdId())
                .build();
    }
}
