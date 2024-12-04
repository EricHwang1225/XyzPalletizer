package com.lgcns.smartwcs.xyzPalletizer.model;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor

public class ResData {
  private int status;
  private String order_id;
}
