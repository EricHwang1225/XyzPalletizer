package com.lgcns.smartwcs.xyzPalletizer.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor

public class LogPltInfo {
  private String plt_id;
  private String loc_cd;
  private String sts_cd;
  private String order_id;
}
