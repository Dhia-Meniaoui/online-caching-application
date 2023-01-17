package com.kastourik12.clients.paymentAPI;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.lang.Nullable;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class PayPalPaymentRequest {
    String total;
    String currency;
    @Nullable
    String clientId;
    @Nullable
    String userId;
    @Nullable
    String type;
    @Nullable
    String redirectUri;
    @Nullable
    String cancelUri;
}
