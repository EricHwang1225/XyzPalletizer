package com.lgcns.smartwcs.interfaces.wms_If.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lgcns.smartwcs.common.client.WMSClient;
import com.lgcns.smartwcs.common.client.model.WMSResponse;
import com.lgcns.smartwcs.common.client.model.XyzResponse;
import com.lgcns.smartwcs.interfaces.if_log.service.IfLogService;
import com.lgcns.smartwcs.xyzPalletizer.model.SkuInfo;
import com.lgcns.smartwcs.xyzPalletizer.reporsitory.PalletizerReporsitory;
import com.lgcns.smartwcs.xyzPalletizer.service.PalletizerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor

public class WmsService {

  private final PalletizerService palletizerService;
  private final IfLogService ifLogService;
  private final WMSClient wmsClient;

  private final String WmsServer = "http://127.0.0.2:7002";


  //=========================================================================================
  // WMS -> WCS
  //=========================================================================================
  public void rcvSkuInfo(SkuInfo skuInfo) throws JsonProcessingException {
    ObjectMapper mapper = new ObjectMapper();

    palletizerService.appendTask(skuInfo);

    String json = mapper.writeValueAsString(skuInfo);
    log.info("Send Cmd : Append task", json);
    ifLogService.saveWmsIfLog("Receive Sku Info","160", mapper.writeValueAsString(json));
  }


  //=========================================================================================
  // WMS <- WCS
  //=========================================================================================
  public void processBarcodeData(int id, List<String > barcodes) throws JsonProcessingException {

    SkuInfo skuInfo = new SkuInfo();

    // Create a unique ID by prepending the current time in hhmmss format
    String uniqueId = generateUniqueId(String.valueOf(id));
    skuInfo.setId(uniqueId);

    for (int i = 0; i < barcodes.size(); i++) {
      String barcode = barcodes.get(i);
      System.out.println("Processing Unique ID: " + uniqueId + ", Barcode: " + barcode);

      // Dynamically call the appropriate setBcd method based on index
      switch (i) {
        case 0:
          skuInfo.setBcd1(barcode);
          break;
        case 1:
          skuInfo.setBcd2(barcode);
          break;
        case 2:
          skuInfo.setBcd3(barcode);
          break;
        case 3:
          skuInfo.setBcd4(barcode);
          break;
        default:
          // Handle the case where there are more barcodes than fields
          System.out.println("Warning: More barcodes than available fields in SkuInfo.");
          break;
      }
    }

    // Send processed data to the upper system
//    sendToWMS(skuInfo);
    // Send processed data to the upper system
    palletizerService.appendTask(skuInfo);
  }


  private void sendToWMS(SkuInfo skuInfo) throws JsonProcessingException {
    // Implement the logic to send data to the upper system
    // This could be another socket connection, HTTP request, etc.
    WMSResponse response = new WMSResponse();
    ObjectMapper mapper = new ObjectMapper();

    String url = WmsServer + "/sendskuinfo";
    String json = mapper.writeValueAsString(skuInfo);

    response = wmsClient.postInterface(url, json);

    System.out.println("Sending data to upper system: Unique ID=" + skuInfo.getId() + ", Barcode=" + skuInfo);

  }

  //=========================================================================================
  //Internal Function
  //=========================================================================================
  private String generateUniqueId(String id) {
    SimpleDateFormat formatter = new SimpleDateFormat("HHmmss");
    String timePrefix = formatter.format(new Date());
    return timePrefix + id;
  }

}
