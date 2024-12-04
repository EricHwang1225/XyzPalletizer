package com.lgcns.smartwcs.req.cst.model;

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
public class CstReqDTO extends BaseDTO {
    private String coCd;
    private String cstCd;
    private String cstNm;
    private String useYn;

    private String ifWcsYn;
    private String ifWcsStatusCd;
    private Integer ifWcsCnt;
    private String ifWcsDt;
    private String ifWcsMsg;
    private String wmsTrackingId;

    private String wcsRegYn;

    public CstReq dtoMapping() {
        return CstReq.builder()
                .coCd(this.getCoCd())
                .cstCd(this.getCstCd())
                .cstNm(this.getCstNm())
                .useYn(this.getUseYn())
                .ifWcsYn(this.getIfWcsYn())
                .ifWcsStatusCd(this.getIfWcsStatusCd())
                .ifWcsCnt(this.getIfWcsCnt())
                .ifWcsDt(this.getIfWcsDt())
                .ifWcsMsg(this.getIfWcsMsg())
                .wmsTrackingId(this.getWmsTrackingId())
                .wcsRegYn(this.getWcsRegYn())
                .regId(this.getRegId())
                .updId(this.getUpdId())
                .build();
    }
}
