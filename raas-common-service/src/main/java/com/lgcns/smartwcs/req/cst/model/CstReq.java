package com.lgcns.smartwcs.req.cst.model;

import com.lgcns.smartwcs.common.model.BaseEntity;
import com.lgcns.smartwcs.req.cst.model.ids.CstReqId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import jakarta.persistence.*;

@Data
@EqualsAndHashCode(callSuper = false)
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "TB_REQ_CST_MST")
@IdClass(CstReqId.class)
@DynamicInsert
@DynamicUpdate
public class CstReq extends BaseEntity {
    @Id
    @Column(name = "CO_CD")
    private String coCd;

    @Id
    @Column(name = "CST_CD")
    private String cstCd;

    @Column(name = "CST_NM")
    private String cstNm;

    @Column(name = "USE_YN")
    private String useYn;

    @Column(name = "IF_WCS_YN")
    private String ifWcsYn;

    @Column(name = "IF_WCS_STATUS_CD")
    private String ifWcsStatusCd;

    @Column(name = "IF_WCS_CNT")
    private Integer ifWcsCnt;

    @Column(name = "IF_WCS_DT")
    private String ifWcsDt;

    @Column(name = "IF_WCS_MSG")
    private String ifWcsMsg;

    @Column(name = "WMS_TRACKING_ID")
    private String wmsTrackingId;

    @Column(name = "WCS_REG_YN")
    private String wcsRegYn;
}
