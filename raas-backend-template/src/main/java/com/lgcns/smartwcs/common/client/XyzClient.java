package com.lgcns.smartwcs.common.client;

import com.lgcns.smartwcs.common.client.model.XyzResponse;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class XyzClient {

  @Autowired
  private RestTemplate restTemplate;

//  @Value("${httpclient.target.wcs.server}")

  private String WCS_SERVER;
  public static final String MIXED_CASE_PALLETIZING = "mixed_case_palletizer";
  public static final Map<String, String> xyzInterface = new HashMap<>();

  @PostConstruct
  public void init() {
    xyzInterface.put(MIXED_CASE_PALLETIZING, WCS_SERVER + MIXED_CASE_PALLETIZING);
  }

  public XyzResponse postInterface(String url , Object request){

    XyzResponse response = null;

    try{
      HttpHeaders headers = new HttpHeaders();
      headers.setContentType(MediaType.APPLICATION_JSON);
      HttpEntity<Object> entity = new HttpEntity<>(request.toString(), headers);
      response = restTemplate.postForObject(url, entity, XyzResponse.class);
    }catch (RestClientException rce){
      response = XyzResponse.builder()
        .code(99)
        .msg(rce.getMessage())
        .build();
    }catch (Exception e){
      response = XyzResponse.builder()
        .code(98)
        .msg(e.getMessage())
        .build();
    }
    return response;
  }

  public XyzResponse getInterface(String url, Object request){

    XyzResponse response = null;

    try{
      HttpHeaders headers = new HttpHeaders();
      headers.setContentType(MediaType.APPLICATION_JSON);
      HttpEntity<Object> entity = new HttpEntity<>(request.toString(), headers);
      response = restTemplate.getForObject(url, XyzResponse.class);
    }catch (RestClientException rce){
      response = XyzResponse.builder()
        .code(99)
        .msg(rce.getMessage())
        .build();
    }catch (Exception e){
      response = XyzResponse.builder()
        .code(98)
        .msg(e.getMessage())
        .build();
    }
    return response;
  }

}
