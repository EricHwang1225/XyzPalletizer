package com.lgcns.smartwcs.xyzPalletizer.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor

public class XyzReport {
  private String order_id;
  private int pick_num;
  private int done_num;
  private int total_num;

  private String id;
  private String type;
  private String status;

  private  String error_code;
  private  String error_msg;

  private String task_id;
  private String to_ws;
}
