package com.lgcns.smartwcs.common.client.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.lgcns.smartwcs.xyzPalletizer.model.ResData;
import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
//@JsonInclude(JsonInclude.Include.NON_NULL)
public class XyzResponse {
  private int code;
  private String msg;
  private ResData data;
  private String errorMsg;

  //================================
  //매 Sku 결과 보고

  private String PID;
  // Picking workspace ID
  private String fromIdCd;
  // Place workspace ID
  private String toIdCd;
  // Place workspace position (Pallet or Buffer)
  private String toPlaceCd;
  // Execution result
  private String exeResult;
}
