package com.lgcns.smartwcs.xyzPalletizer.reporsitory;

import com.lgcns.smartwcs.xyzPalletizer.model.*;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Repository
@Mapper

public interface PalletizerReporsitory {

  // WCS -> XYZ

  //Internal
  void insertPalletData(LogPltInfo palletInfo);
  void updatePalletData(Integer pltId, Integer pltLocCd);
  void insertBoxData(LogSkuInfo logSkuInfo);
  void completeBoxInPallet(String pltId);
  void insertOrderInfo(@Param("params") Map<String, Object> params);

  String getPalletPid (String pltLocNo);
  List<String> getOrderId();
  String getPltStsCd(String id);
  List<String> getLocCd(String bcr1, String bcr2);

  SkuInfo getSkuInfo(String bcd1, String bcd2, String bcd3, String bcd4);

  void uploadSkuInfo(SkuMasterInfo skuMasterInfo);

  ArrayList<String> getPltID(String toWs, String sts_cd1, String sts_cd2);
}
