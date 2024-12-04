package com.lgcns.smartwcs.xyzPalletizer.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor

public class LogSkuInfo {
  private String id;
  private String sts_cd;
  private String palletId;
  private String category;
  private String bcr1;
  private String bcr2;
}
