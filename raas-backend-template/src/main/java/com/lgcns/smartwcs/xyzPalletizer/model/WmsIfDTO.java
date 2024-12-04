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

public class WmsIfDTO {

  private long wmsIfLogNo;
  private String wmsIfNm;
  private String wmsIfTrakNo;
  private String wmsIfJson;

  private String wcsIfYn;
  private String wcsIfStatCd;
  private Integer wcsIfCnt;
  private String wcsIfMsg;
  private String regId;

  @CreationTimestamp
  @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
  private LocalDateTime regDt;




}
