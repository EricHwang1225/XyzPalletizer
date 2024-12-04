package com.lgcns.smartwcs.interfaces.wms_If.controller;

import com.lgcns.smartwcs.common.client.model.WMSResponse;
import com.lgcns.smartwcs.common.client.model.XyzResponse;
import com.lgcns.smartwcs.interfaces.wms_If.service.WmsService;
import com.lgcns.smartwcs.xyzPalletizer.model.ReqDTO;
import com.lgcns.smartwcs.xyzPalletizer.model.SkuInfo;
import com.lgcns.smartwcs.xyzPalletizer.service.PalletizerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.CompletableFuture;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/wms")

public class WmsController {

  private final WmsService wmsService;

  @PostMapping(value = "/skuInfo", produces = {"application/json"})
  public void rcvSkuInfo(SkuInfo skuInfo) throws Exception{
    wmsService.rcvSkuInfo(skuInfo);
  }


}
