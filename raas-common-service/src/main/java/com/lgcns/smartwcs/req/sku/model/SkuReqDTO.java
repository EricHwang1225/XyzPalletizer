package com.lgcns.smartwcs.req.sku.model;

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
public class SkuReqDTO extends BaseDTO {
    private String coCd;
    private String cstCd;
    private String skuCd;
    private String bcdNo;

    private String skuNm;
    private String imageUrl;
    private String skuStackMethod;
    private String useYn;
    private String bcdType;
    private String bcdUseYn;
    private String ifWcsYn;
    private String ifWcsStatusCd;
    private Integer ifWcsCnt;
    private String ifWcsDt;
    private String ifWcsMsg;
    private String wmsTrackingId;

    private String wcsRegYn;

    public SkuReq dtoMapping() {
        return SkuReq.builder()
                .coCd(this.getCoCd())
                .cstCd(this.getCstCd())
                .skuCd(this.getSkuCd())
                .bcdNo(this.getBcdNo())
                .skuNm(this.getSkuNm())
                .imageUrl(this.getImageUrl())
                .skuStackMethod(this.getSkuStackMethod())
                .useYn(this.getUseYn())
                .bcdType(this.getBcdType())
                .bcdUseYn(this.getBcdUseYn())
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
