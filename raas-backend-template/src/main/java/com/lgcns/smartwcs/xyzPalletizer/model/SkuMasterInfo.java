package com.lgcns.smartwcs.xyzPalletizer.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor

public class SkuMasterInfo {
  private String co_cd;
  private Date reg_dt;
  private String cst_cd;
  private String box_bcr;
  private String logics_bcr;
  private String sku_name;
  private String category;

  private float length;
  private float width;
  private float height;
  private float weight;
}
