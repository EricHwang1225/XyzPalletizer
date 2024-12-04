package com.lgcns.smartwcs.common.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BaseDTO {
    private String regId;
    private String regDt;
    private String updId;
    private String updDt;
}
