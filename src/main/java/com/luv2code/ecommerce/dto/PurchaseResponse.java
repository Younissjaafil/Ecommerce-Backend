package com.luv2code.ecommerce.dto;

import lombok.Data;

@Data
public class PurchaseResponse {

  // Use this class to send back a Java object as JSON
  // we can use final or @NotNull
  private final String orderTrackingNumber;

}
