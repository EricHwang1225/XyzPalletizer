package com.lgcns.smartwcs.common.client;

import com.lgcns.smartwcs.interfaces.wms_If.service.WmsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;


import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
public class BcrClient {

  public void startListening() {

  }

//  private final WmsService wmsService;
//
//  @Value("${barcode.reader.ip}")
//  private String barcodeReaderIp;
//
//  @Value("${barcode.reader.port}")
//  private int barcodeReaderPort;
//
//  public BcrClient(WmsService wmsService) {
//    this.wmsService = wmsService;
//  }
//
//  public void startListening() {
//    try (Socket clientSocket = new Socket("127.0.0.1", 8001);
//         BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()))) {
//
//      System.out.println("Connected to barcode reader at " + "127.0.0.1" + ":" + "8001");
//
//      String inputLine;
//      while ((inputLine = in.readLine()) != null) {
//        // Assuming inputLine contains multiple barcode data separated by ";"
//        String[]barcodeEntries = inputLine.split(";");
//
//        Map<Integer, List<String>> barcodeDataMap = new HashMap<>();
//
//        for (String entry : barcodeEntries) {
//          String[]data = entry.split(",");
//          if (data.length == 2) {
//            int id = Integer.parseInt(data[0]);
//            String barcode = data[1];
//
//            // Get the list of barcodes for this ID, or create a new list if it doesn't exist
//            List<String> barcodes = barcodeDataMap.getOrDefault(id, new ArrayList<>());
//            barcodes.add(barcode);
//            barcodeDataMap.put(id, barcodes);
//          }
//        }
//
//        // Pass the map of barcode data to the processBarcodeData method
//        for (Map.Entry<Integer, List<String>> entry : barcodeDataMap.entrySet()) {
//          int id = entry.getKey();
//          List<String> barcodes = entry.getValue();
//          wmsService.processBarcodeData(id, barcodes);
//          }
//
//      }
//    } catch (Exception e) {
//      e.printStackTrace();
//    }
//  }
}
