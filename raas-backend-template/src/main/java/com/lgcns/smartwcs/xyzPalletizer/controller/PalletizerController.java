package com.lgcns.smartwcs.xyzPalletizer.controller;

import com.lgcns.smartwcs.common.client.model.XyzResponse;
import com.lgcns.smartwcs.xyzPalletizer.model.*;
import com.lgcns.smartwcs.xyzPalletizer.service.PalletizerService;
import jakarta.ws.rs.PathParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.concurrent.CompletableFuture;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/test")

public class PalletizerController {

  private final PalletizerService palletizerService;

  //=========================================================================================
  // Excel -> ECS
  //=========================================================================================
  @PostMapping(value = "/api/upload/file/skuinfo", produces = {"application/json"})
  public ResDTO uploadSkuInfo(@RequestParam("file") MultipartFile file) throws Exception{
      return palletizerService.uploadFileSkuinfo(file);
  }

  @PostMapping(value = "/api/upload/skuinfo", produces = {"application/json"})
  public ResDTO uploadSkuInfo(@RequestBody SkuMasterInfo skuMasterInfo) throws Exception{
    return palletizerService.uploadSkuinfo(skuMasterInfo);
  }

  //=========================================================================================
  // WCS -> XYZ
  //=========================================================================================
  @GetMapping(value = "/api/status", produces = {"application/json"})
  public XyzResponse getTheSystemStatus() throws Exception{
    return palletizerService.getTheSystemStatus();
  }

  @PostMapping(value = "/adaptor/api/wcs/workspace/ready", produces = {"application/json"})
  public CompletableFuture<XyzResponse> notifiesThatTheWorkspaceIsReady(@RequestBody ReqDTO reqDTO ) throws Exception{
    return palletizerService.workspaceReady(reqDTO);
  }

  @PostMapping(value = "/adaptor/api/wcs/order/abort", produces = {"application/json"})
  public XyzResponse abortOrder(@RequestBody String orderID) throws Exception{
    return palletizerService.cancelOrder(orderID);
  }

  @PostMapping(value = "/adaptor/api/wcs/order", produces = {"application/json"})
  public XyzResponse createAnOrder(@RequestBody OrderInfo orderInfo) throws Exception{
    return palletizerService.sendOrder(orderInfo);
  }

  @PostMapping(value = "/adaptor/api/wcs/order/append_task", produces = {"application/json"})
  public XyzResponse appendTask(@RequestBody SkuInfo skuInfo) throws Exception{
    return palletizerService.appendTask(skuInfo);
  }




  //=========================================================================================
  // WCS <- XYZ
  //=========================================================================================
  @PostMapping (value = "/api/workspace_status", produces = {"application/json"})
  public ResDTO workspaceStatus(@RequestBody XyzReport xyzReport) throws Exception{
   return palletizerService.reportWorkspace(xyzReport);
  }

  @PostMapping (value = "/api/exception", produces = {"application/json"})
  public ResDTO reportError(@RequestBody XyzReport xyzReport) throws Exception{
    return palletizerService.reportError(xyzReport);
  }

  @PostMapping (value = "/api/cycle_result", produces = {"application/json"})
  public ResDTO cycleResult(@RequestBody XyzReport xyzReport) throws Exception{
    return palletizerService.cycleResult(xyzReport);
  }

  @PostMapping (value = "/api/order_result", produces = {"application/json"})
  public ResDTO orderResult(@RequestBody XyzReport xyzReport) throws Exception{
    return palletizerService.reportExcutionResult(xyzReport);
  }


}
