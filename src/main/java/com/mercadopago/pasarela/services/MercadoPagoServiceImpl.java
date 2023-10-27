package com.mercadopago.pasarela.services;

import com.mercadopago.MercadoPagoConfig;
import com.mercadopago.client.preference.*;
import com.mercadopago.exceptions.MPApiException;
import com.mercadopago.exceptions.MPException;
import com.mercadopago.resources.preference.Preference;

import java.math.BigDecimal;
import java.util.*;

public class MercadoPagoServiceImpl {
    private static final String MERCADO_PAGO_ACCESS_TOKEN = "TEST-8103593810914502-092322-37a37e8cdc6ec45aca8cac6a82311caa-1488432555";

    public String createPreference(double amount, int meses) {
        try {

            MercadoPagoConfig.setAccessToken(MERCADO_PAGO_ACCESS_TOKEN);


            List<PreferenceItemRequest> items = new ArrayList<>();
            PreferenceItemRequest saleItem = PreferenceItemRequest.builder()
                    .id("product-item-" + amount)
                    .title(meses + amount + " USD:")
                    .quantity(1)
                    .currencyId("USD")
                    .unitPrice(new BigDecimal(amount))
                    .build();

            items.add(saleItem);

            PreferencePaymentMethodsRequest paymentMethods =
                    PreferencePaymentMethodsRequest.builder()
                            .installments(1)
                            .build();

            PreferenceBackUrlsRequest backUrls = PreferenceBackUrlsRequest.builder()
                    .success("http://localhost:5173/success")
                    .pending("http://localhost:5173/pending")
                    .failure("http://localhost:5173/failure")
                    .build();

            PreferenceRequest preferenceRequest = PreferenceRequest.builder()
                    .items(items)
                    .backUrls(backUrls)
                    .notificationUrl("https://46cd-2800-200-f410-304b-744f-8c78-eebb-593a.ngrok-free.app/api/pagos/notification") //Webhook
                    .paymentMethods(paymentMethods)
                    .autoReturn("approved")
                    .build();

            PreferenceClient client = new PreferenceClient();
            Preference preference = client.create(preferenceRequest);
            return preference.getInitPoint();
        } catch (MPException | MPApiException e) {
            e.printStackTrace();
            return "Error creating preferences";
        }
    }
}
