package com.lgcns.smartwcs.xyzPalletizer.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lgcns.smartwcs.common.client.XyzClient;
import com.lgcns.smartwcs.common.client.model.XyzResponse;
import com.lgcns.smartwcs.common.exception.InterfaceException;
import com.lgcns.smartwcs.interfaces.if_log.service.IfLogService;
import com.lgcns.smartwcs.xyzPalletizer.model.*;
import com.lgcns.smartwcs.xyzPalletizer.reporsitory.PalletizerReporsitory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.*;
import org.springframework.orm.hibernate5.HibernateTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.CompletableFuture;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;


@Slf4j
@Service
@RequiredArgsConstructor

public class PalletizerService {

  private final PalletizerReporsitory palletizerReporsitory;
  private final XyzClient xyzClient;
  private final IfLogService ifLogService;
  private final Map<Integer, Integer> incrementMap = new HashMap<>();

  private final String xyzServer = "http://127.0.0.1:7002";

  private static final AtomicInteger sequence = new AtomicInteger(0);
  private static final ConcurrentHashMap<String, AtomicInteger> sequenceMap = new ConcurrentHashMap<>();
  private static String lastTimeStamp = "";

  //=========================================================================================
  // Excel -> ECS
  //=========================================================================================

  //Excel 받은 데이터를 DB에 저장
  public ResDTO uploadFileSkuinfo(MultipartFile file) throws IOException {
    ResDTO resDTO = new ResDTO();
//
//    List<SkuInfo> products = new ArrayList<>();
//    try (InputStream is = file.getInputStream(); Workbook workbook = new XSSFWorkbook(is)) {
//      Sheet sheet = workbook.getSheetAt(0);
//      for (Row row : sheet) {
//        if (row.getRowNum() == 0) { // Skip header row
//          continue;
//        }
//        SkuInfo skuInfo = new SkuInfo();
//        skuInfo.setBcd1(getCellValueAsString(row.getCell(0)));
//        if (skuInfo.getBcd1() == null || skuInfo.getBcd1().isEmpty()) {
//          continue; // Skip rows where bcd1 is empty
//        }
//        skuInfo.setBcd2(getCellValueAsString(row.getCell(1)));
//        skuInfo.setLength((float) row.getCell(2).getNumericCellValue());
//        skuInfo.setWidth((float) row.getCell(3).getNumericCellValue());
//        skuInfo.setHeight((float) row.getCell(4).getNumericCellValue());
//        skuInfo.setWeight((float) row.getCell(5).getNumericCellValue());
//        skuInfo.setTarget_plt((int) row.getCell(6).getNumericCellValue());
//        products.add(skuInfo);
//      }
//    }
//    for (SkuInfo skuInfo : products) {
//      palletizerReporsitory.uploadSkuInfo(skuInfo);
//    }
    return resDTO;
  }

  private String getCellValueAsString(Cell cell) {
    if (cell == null) {
      return null;
    }
    return cell.getCellType() == CellType.STRING ? cell.getStringCellValue() : String.valueOf((int) cell.getNumericCellValue());
  }


  public ResDTO uploadSkuinfo(SkuMasterInfo skuMasterInfo) throws IOException {
    ResDTO resDTO = new ResDTO();

    try {
      palletizerReporsitory.uploadSkuInfo(skuMasterInfo);
      log.info("Save Skuinfo", skuMasterInfo);
      resDTO.setCode(0);
    } catch (Exception e) {
      resDTO.setCode(999);
    }
    return resDTO;
  }

  //=========================================================================================
  // WCS -> XYZ
  //=========================================================================================

  //명령 전송 전 로봇 상태 확인
  public XyzResponse getTheSystemStatus() throws JsonProcessingException, InterfaceException {
    XyzResponse response = new XyzResponse();
    ObjectMapper mapper = new ObjectMapper();

    String url = xyzServer + "/api/status";
    String json = mapper.writeValueAsString(null);

    response.setCode(999);
    response = xyzClient.getInterface(url, json);

    try {
      log.info("Send Cmd : Get the System Status", json);
      ifLogService.saveWcsIfLog("GetTheSystemStatus", mapper.writeValueAsString(json), "11", null, null);
      log.debug(response.toString());
      ifLogService.saveWcsIfLog("GetTheSystemStatus", mapper.writeValueAsString(response), "12", null, null);
    } catch (Exception e) {
      throw new InterfaceException("Wrong format", e.getMessage());
    }
    return response;
  }

  //적재 가능 Pallet 정보 전송
  public CompletableFuture<XyzResponse> workspaceReady(ReqDTO reqDTO) throws JsonProcessingException {
    return CompletableFuture.supplyAsync(() -> {

      XyzResponse response = new XyzResponse();
      XyzResponse readyResponse = new XyzResponse();
      ObjectMapper mapper = new ObjectMapper();
      ReqDTO reqXYZ = new ReqDTO();

      String url = xyzServer + "/adaptor/api/wcs/workspace/ready";
      String palletLogCd = "10";

      try {//로봇 상태 확인
        readyResponse.setCode(999);
        readyResponse = getTheSystemStatus();
      } catch (JsonProcessingException | InterfaceException e) {
        throw new RuntimeException(e);
      }

      if (response.getCode() != 99) {
        try {
          String json = mapper.writeValueAsString(reqDTO);
          response.setCode(999);
          response = xyzClient.postInterface(url, json);

          if (response.getCode() == 0) {
            log.info("Send Cmd : Success Notifies that workspace is ready", json);
            updatePalletHistory(palletLogCd, reqDTO.getId(), null);
          } else {
            log.info("Send Cmd : Fail Notifies that workspace is ready", json);
          }
        } catch (JsonProcessingException e) {
          throw new RuntimeException(e);
        }
      } else {
        log.info("Send Cmd : Need to robot ready");
      }
      return response;
    });
  }


  //Work complete 일작업 종료 => Studio Max에서 전체 Data Clear
  //Pallet 진행 중 상태 종료 상태로 변경
  public XyzResponse cancelOrder(String orderId) throws JsonProcessingException {
    XyzResponse response = new XyzResponse();
    ObjectMapper mapper = new ObjectMapper();

    String url = xyzServer + "/adaptor/api/wcs/order/abort";
    String json = mapper.writeValueAsString(orderId);

    response.setCode(999);
    response = xyzClient.postInterface(url, json);

    if (response.getCode() == 0) {
      for (int i = 1; i <= 6; i++) {
        updatePalletHistory("100", String.valueOf(i), null);
      }
    } else {
      response.setErrorMsg(response.getErrorMsg());
    }
    return response;
  }

  // 작업시작 시 한번만 전송 함 (하루 1회)
  public XyzResponse sendOrder(OrderInfo orderInfo) throws JsonProcessingException {

    XyzResponse response = new XyzResponse();
    ObjectMapper mapper = new ObjectMapper();
    Map<String, Object> params = new HashMap<>();

    orderInfo.setOrder_id(generateOrderId());
    String orderStsCd = "10";

    String url = xyzServer + "/adaptor/api/wcs/order";
    String json = mapper.writeValueAsString(orderInfo);

    try {
      response.setCode(999);
      response = xyzClient.postInterface(url, json);
    } catch (Exception e) {
      throw new RuntimeException(e);
    }

    if (response.getCode() == 0) {
      params.put("orderInfo", orderInfo);
      params.put("orderStsCd", orderStsCd);

      palletizerReporsitory.insertOrderInfo(params);
      log.info("Send Cmd : Success Notifies that order is ready", json);
      ifLogService.saveWcsIfLog("CreateAnOrder", mapper.writeValueAsString(json), "140", null, null);
    } else {
      log.info("Send Cmd : Order send fail", json);
    }
    return response;
  }

  //BCR 스캔 정보와 목적지 XYZ로 전송
  public XyzResponse appendTask(SkuInfo skuInfo) throws JsonProcessingException {
    XyzResponse response = new XyzResponse();
    ObjectMapper mapper = new ObjectMapper();
    OrderInfo orderInfo = new OrderInfo();
    ArrayList<Item> items = new ArrayList<>();
    ArrayList<String> bcrs = new ArrayList<>();
    Item item = new Item();
    String plcResult = "0";
    String boxId = "";
    ArrayList<String> toPallet = new ArrayList<>();
    LogSkuInfo logSkuInfo = new LogSkuInfo();

    //현재 진행 중인 Order ID 조회
    String orderId = findOrderId();

    if (Objects.equals(skuInfo.getId(), "Noread")) {
      //BCR ID 값이 Noread 일 경우 컨베이어 정지
      plcResult = stopBcrCV(2);
    } else if (orderId == null) {
      //Order 정보가 여러개 일경우
      plcResult = stopBcrCV(3);
    }else{
      boxId = generateBoxId(skuInfo.getId()); //유니크한 박스 ID 생성

      //바코드 정보#1로 Box 목적지 조회
      List<String> loc_cd = palletizerReporsitory.getLocCd(skuInfo.getBcd1(), skuInfo.getBcd2());
      if (Objects.equals(loc_cd.get(0), "0") || loc_cd.get(0) == null ||loc_cd.size() > 1) {
//        //바코드 정보#2로 Box 목적지 조회
//        String loc_cd2 = palletizerReporsitory.getLocCd(skuInfo.getBcd2());
//        if (Objects.equals(loc_cd2, "0") || loc_cd2 == null) {
//          //Location CD 조회값이 0 일 경우 컨베이어 정지 로직
          plcResult = stopBcrCV(4);
//        } else {
//          toPallet.add(loc_cd2);
//          item.setTo(toPallet);
//          item.setBarcode(skuInfo.getBcd1());
//        }
      } else {
        toPallet.add(loc_cd.get(0));
        item.setTo(toPallet);
        if(skuInfo.getBcd1() != null){
          bcrs.add(skuInfo.getBcd1());
          logSkuInfo.setBcr1(skuInfo.getBcd1());
        } else if (skuInfo.getBcd2() != null) {
          bcrs.add(skuInfo.getBcd2());
          logSkuInfo.setBcr2(skuInfo.getBcd2());
        }
        item.setBarcode(bcrs);
        orderInfo.setOrder_id(orderId);
        item.setTask_id(boxId);
        item.setTarget_num(1);
        items.add(item);
        orderInfo.setItems(items);
        //Box data sku info
        logSkuInfo.setSts_cd("10");
        logSkuInfo.setCategory(loc_cd.get(0));
        logSkuInfo.setId(boxId);
        //Data 완성 후 XYZ 송신
        if (item.getTo() != null){
          String url = xyzServer + "/adaptor/api/wcs/order/append_task";
          String json = mapper.writeValueAsString(orderInfo);
          try {
//            response.setCode(999);
//            response = xyzClient.postInterface(url, json);
            updateBoxHistory(logSkuInfo);
          } catch (Exception e) {
            throw new RuntimeException(e);
          }
          log.info("Send Cmd : Append task", json);
          ifLogService.saveWcsIfLog("AppendTask", mapper.writeValueAsString(json), "160", null, null);
        }else {
          log.info("ID/Pallet info fail");
        }
      }
    }
    return response;
  }

  //=========================================================================================
  // WCS <- XYZ
  //=========================================================================================

  //완료 Pallet 보고
  //작업 상태 코드가 50(작업 중) Pallet 작업종료로 Insert
  public ResDTO reportWorkspace(XyzReport xyzReport) throws JsonProcessingException {
    ResDTO resDTO = new ResDTO();

    String repPltSts = xyzReport.getStatus();
    String sts_cd1 = "50";
    String sts_cd2 = "10";
    String pltStsCheck = "NG";

    resDTO.setCode(999);
    resDTO.setMsg(pltStsCheck);

    String pltId = findPltId(xyzReport.getId(), sts_cd1, sts_cd2);
    if(pltId != null){
      try {
        switch (repPltSts) {
          case "full":
            updatePalletHistory("100", xyzReport.getId(), pltId);
            break;
          case "empty":
            // XYZ report 정보와 비교
            String getPltStsCd = palletizerReporsitory.getPltStsCd(xyzReport.getId());
            if(getPltStsCd.equals("10")) pltStsCheck = "OK";
            log.info("Pallet status check : {}", pltStsCheck);
            break;
          default:
            log.info("Wrong cmd received from XYZ, Pallet status check : {}", repPltSts);
            break;
        }
        resDTO.setCode(0);
        resDTO.setMsg("ok");
      }catch (Exception e){
        e.printStackTrace();
      }
    }else {
      log.info("Pallet status check fail");
    }
    return resDTO;
  }

  //로봇 에러 보고
  //작업 진행 가능 에러, 불가 에러 구분 필요
  public ResDTO reportError(XyzReport xyzReport){
    ResDTO resDTO = new ResDTO();

    return resDTO;
  }

  //매 Sku 마다 적재 작업 보고
  //작업 상태 코드가 10(작업대기) Pallet 작업 진행으로 insert
  //To ID Code로 Pallet ID 검색 후 Insert 실행
  public ResDTO reportExcutionResult(XyzReport skuInfo){
    ResDTO resDTO = new ResDTO();

    return resDTO;
  }

  public ResDTO cycleResult(XyzReport xyzReport) {
    ResDTO resDTO = new ResDTO();
    LogSkuInfo logSkuInfo = new LogSkuInfo();
    String sts_cd1 = "50";
    String sts_cd2 = "10";
    //Category 로 현재 진행 중인 Pallet ID 검색
    String palletId = findPltId(xyzReport.getTo_ws(), sts_cd1, sts_cd2);
    if (palletId !=null) {
      //해당 Sku를 찾아서 Status cd 및 Pallet ID insert
      logSkuInfo.setPalletId(palletId);
      logSkuInfo.setId(xyzReport.getTask_id());
      logSkuInfo.setCategory(xyzReport.getTo_ws());
      logSkuInfo.setSts_cd("50");
      try {
        updateBoxHistory(logSkuInfo);
        updatePalletHistory("50", xyzReport.getTo_ws(), palletId);
      } catch (JsonProcessingException e) {
        throw new RuntimeException(e);
      }
      resDTO.setCode(0);
      resDTO.setMsg("ok");
    }else {
      log.info("Find Mutiple Category error");
    }
    //결과 값 회신
    return resDTO;
  }


  //=========================================================================================
  //Internal Function
  //=========================================================================================

  //Pallet 실적 저장
  // Pallet Log Code (10:신규, 50:진행 중, 100:종료)
  // Pallet ID 생성은 Code "10" 에서만 진행 이후 Pallet Location 통해 Pallet ID 가져옴
  @Async
  public void updatePalletHistory(String palletLogCd, String pltLocNo, String rcvPltId) throws JsonProcessingException {
    String getPltId;
    String orderId = findOrderId();

     switch (palletLogCd){
       case "10" :
         String pltId = generatePltId(pltLocNo);
         insertPalletInfo(pltId, pltLocNo ,palletLogCd, orderId);
         log.info("Pallet operation ready");
         break;
       case "50" :
         insertPalletInfo(rcvPltId, pltLocNo, palletLogCd,orderId);
         log.info("Pallet operation start");
         break;
       case "100" :
         insertPalletInfo(rcvPltId, pltLocNo, palletLogCd,orderId);
         //Sku Status cd 50 인걸 찾아어 모두 100으로 update
         palletizerReporsitory.completeBoxInPallet(rcvPltId);
         log.info("Pallet operation complete");
         break;
       default:
         log.info("Pallet Location ID Error");
         break;
     }
  }

  //Box 실적 저장
  //Box Log Code (10:BCR , 30:Buffer 적재, 50:Pallet 적재, 100:완료(Pallet history에서 완료 처리 함))
  //Cancel order 에서는 전체 미 완료 Box 완료 처리
  //Execution Result (매 Cycle 보고) 에서 Box 별 업데이트
  //BCR 이후는 초기값 "10"으로 업데이트
  //Location code : Pallet : 10/20/30/40/50/60
  //Location code : Buffer : 11/21/31/41/51/61 [미사용]
  @Async
  public void updateBoxHistory(LogSkuInfo logSkuInfo) throws JsonProcessingException {
    palletizerReporsitory.insertBoxData(logSkuInfo);
    log.info("Update Box status");
  }

  //유니크 ID 값 생성
  public static String generateId(String prefix, String dateFormat) {
    // 현재 시간 가져오기
    String timeStamp = new SimpleDateFormat(dateFormat).format(new Date());

    // 증가값 가져오기 및 증가
    synchronized (PalletizerService.class) {
      if (!timeStamp.equals(lastTimeStamp)) {
        sequence.set(0);
        lastTimeStamp = timeStamp;
      }
    }
    int seq = sequence.incrementAndGet();
    if (seq > 99) {
      sequence.set(1);
      seq = 1;
    }
    // 유니크 ID 생성
    return String.format("%s%s%02d", prefix, timeStamp, seq);
  }
  //Pallet ID 생성
  public static String generatePltId(String pltLocNo) {
    if (pltLocNo == null || pltLocNo.length() != 1 || !pltLocNo.matches("[1-6]")) {
      throw new IllegalArgumentException("Input must be a single digit between 1 and 6.");
    }
    return generateId(pltLocNo, "HHmmss");
  }
  //Order ID 생성
  public static String generateOrderId() {
    return generateId("", "yyyyMMdd");
  }
  // 박스 ID 생성
  public static String generateBoxId(String bcrId) {
    return generateId(bcrId, "MMddHHmmss");
  }

  //BCR CV Control
  //"1"이 아닌값이 입력되면 CV Stop Cmd
  public static String stopBcrCV(int result){
    String plcResult = "";
    switch (result){
      case 1 :
        log.info("CV Start");
        case 2 :
          log.info("Barcode Noread");
          case 3 :
            log.info("Order ID error. Multiple Order ID");
            case 4 :
              log.info("No Destination");
              break;
      default:
        plcResult = "00";
    }
    return plcResult;
  }

  public String findOrderId () {
    String orderId = null;
    ArrayList<String> orderIds = (ArrayList<String>) palletizerReporsitory.getOrderId();

    if (orderIds.size() > 1) {
      log.info("Find Order ID error");
    } else {
      orderId = orderIds.get(0);
    }
    return orderId;
  }

  public String findPltId(String pltLocNo, String sts_cd1, String sts_cd2) {
    String pltId = null;
    ArrayList<String> pltIds = (ArrayList<String>) palletizerReporsitory.getPltID(pltLocNo, sts_cd1, sts_cd2);

    if (pltIds.size() == 1) {
      pltId = pltIds.get(0);
      log.info("Found Pallet ID :{}", pltId);
    } else {
      log.info("Find Pallet ID error");
    }
    return pltId;
  }

  public void insertPalletInfo(String plt_id, String loc_cd, String sts_cd, String order_id) throws JsonProcessingException {
    LogPltInfo pltInfo = new LogPltInfo();
    pltInfo.setPlt_id(plt_id);
    pltInfo.setLoc_cd(loc_cd);
    pltInfo.setSts_cd(sts_cd);
    pltInfo.setOrder_id(order_id);

    try{
      palletizerReporsitory.insertPalletData(pltInfo);
    }catch (Exception e){
      e.printStackTrace();
    }
  }
}



