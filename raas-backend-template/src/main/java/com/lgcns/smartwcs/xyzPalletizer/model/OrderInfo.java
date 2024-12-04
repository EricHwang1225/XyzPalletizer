package com.lgcns.smartwcs.xyzPalletizer.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor

public class OrderInfo {
  private String order_id;
  private int order_type;
  private ArrayList<OrderItem> orderItems;
  private ArrayList<Item> items;
}
