package com.lgcns.smartwcs.xyzPalletizer.model;


import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor

public class WcsIfDTO {

  private long wcsIfLogNo;
  private String wcsIfNm;
  private String wcsIfTrakNo;
  private String wcsIfJson;

  private String wmsIfYn;
  private String wmsIfStatCd;
  private Integer wmsIfCnt;
  private String wmsIfMsg;
  private String regId;

  @CreationTimestamp
  @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
  private LocalDateTime regDt;




}
