package com.kastourik12.clients.paymentAPI;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PaymentDTO {
  private String paymentId ;
  private String payerId ;
  private String Amount;
  private String currency;
  private String userId;
}
