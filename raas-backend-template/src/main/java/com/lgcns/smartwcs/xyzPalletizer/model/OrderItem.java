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

public class OrderItem {
  private SkuInfo skuInfo;
  private ArrayList<String> from;
  private ArrayList<String> to;
  private int target_num;
}
