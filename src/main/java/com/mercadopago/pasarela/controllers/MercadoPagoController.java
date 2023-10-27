package com.mercadopago.pasarela.controllers;


import com.mercadopago.pasarela.services.MercadoPagoService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

import java.util.Map;


@RestController
@RequestMapping("/api/pagos")
public class MercadoPagoController {

    @Autowired
    private MercadoPagoService mercadoPagoService;

    @GetMapping("/create-preferences")
    public Map<String, String> createPreferences() {

        Map<String, String> preferenceIds = new HashMap<>();

        try {
            String sale10PreferenceId = mercadoPagoService.createPreference(15.0, 1);
            String sale25PreferenceId = mercadoPagoService.createPreference(40.0, 3);

            preferenceIds.put("sale10", sale10PreferenceId);
            preferenceIds.put("sale25", sale25PreferenceId);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return preferenceIds;
    }


}
