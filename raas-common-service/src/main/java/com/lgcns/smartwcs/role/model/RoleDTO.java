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

import com.fasterxml.jackson.annotation.JsonInclude;
import com.lgcns.smartwcs.common.model.BaseDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@EqualsAndHashCode(callSuper = false)
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class RoleDTO extends BaseDTO {

    private String coCd;
    private String cntrCd;
    private String roleCd;
    private String roleNm;
    private String useYn;

    public Role dtoMapping() {

        return Role.builder()
                .coCd(this.getCoCd())
                .cntrCd(this.getCntrCd())
                .roleCd(this.getRoleCd())
                .roleNm(this.getRoleNm())
                .useYn(this.getUseYn())
                .regId(this.getRegId())
                .updId(this.getUpdId())
                .build();
    }
}
