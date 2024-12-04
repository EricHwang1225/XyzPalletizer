/*
 * Copyright (C) 2012 LG CNS Inc.
 * All right reserved
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포 사용하는 행위를 금지합니다.
 *
 * @version $id$
 */
package com.lgcns.smartwcs.interfaces.if_log.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicInsert;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Data
@EqualsAndHashCode(callSuper = false)
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@DynamicInsert
@Entity
@Table(name = "TB_PALLETIZER_WCS_IF_LOG")
public class WcsIfLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "WCS_IF_LOG_NO")
    private Long wcsIfLogNo;

    @Column(name = "WCS_IF_NM")
    private String wcsIfNm;

    @Column(name = "WCS_IF_TRAK_NO")
    private String wcsIfTrakNo;

    @Column(name = "PICK_NO")
    private String pickNo;

    @Column(name = "WCS_IF_JSON")
    private String wcsIfJson;

    @Column(name = "WMS_IF_YN")
    private String wmsIfYn;

    @Column(name = "WMS_IF_STAT_CD")
    private String wmsIfStatCd;

    @Column(name = "WMS_IF_CNT")
    private Integer wmsIfCnt;

    @Column(name = "WMS_IF_MSG")
    private String wmsIfMsg;

    @Column(name = "WMS_IF_JSON")
    private String wmsIfJson;

    @Column(name = "REG_ID")
    private String regId;

    @Column(name = "REG_DT")
    @CreationTimestamp
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime regDt;

}
