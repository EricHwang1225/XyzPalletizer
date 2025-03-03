/*
 * Copyright (C) 2012 LG CNS Inc.
 * All right reserved
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포 사용하는 행위를 금지합니다.
 *
 * @version $id$
 */
package com.lgcns.smartwcs.interfaces.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * <PRE>
 * Data Receive Format Entity 클래스
 * </PRE>
 *
 * @author $Author$
 * @version $Id$
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SkuMasterDataFormat {

    /**
     * tracking ID
     */
    @JsonProperty("trackingId")
    @Schema(example = "tracking ID")
    private String trackingId;

    /**
     * count
     */
    @JsonProperty("count")
    @Schema(example = "count")
    private Integer count;


    /**
     * Sku Master 목록
     */
    private List<SkuMaster> dataList;

    /**
     * data 목록
     */
    private SkuMaster data;

}
