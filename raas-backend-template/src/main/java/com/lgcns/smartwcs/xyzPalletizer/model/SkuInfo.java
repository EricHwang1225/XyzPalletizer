package com.lgcns.smartwcs.xyzPalletizer.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor

public class SkuInfo {
  private String  id;
  private String  type;

  private float length;
  private float width;
  private float height;
  private float weight;

  private float load_bearing;
  private int barcode_direction;
  private ArrayList<String> direction_options;

  // 추가 Parameter
  private int target_plt;
  private String sku_Nm;

  private String  bcd1;
  private String  bcd2;
  private String  bcd3;
  private String  bcd4;
}
