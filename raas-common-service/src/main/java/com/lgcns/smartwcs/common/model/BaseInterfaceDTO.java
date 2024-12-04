package com.lgcns.smartwcs.common.model;

import com.lgcns.smartwcs.common.utils.CommonUtils;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.springframework.util.StringUtils;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class BaseInterfaceDTO {
    private String trackingId;
    private String regId;
    private String regDt;
    private String updId;
    private String updDt;

    public void initRegDt() {
        if (!StringUtils.hasText(this.getRegDt())) {
            this.setRegDt(CommonUtils.getCurrentDate());
        }
    }

    public void initRegId() {
        if (!StringUtils.hasText(this.getRegId())) {
            this.setRegId(this.getTrackingId());
        }
    }
}
