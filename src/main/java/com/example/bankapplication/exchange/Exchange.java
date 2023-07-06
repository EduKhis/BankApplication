package com.example.bankapplication.exchange;
import com.example.bankapplication.model.Currency;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.*;


public class Exchange {
    public static void main(String[] args) throws IOException {
        convertRate("RUB", "USD");
    }
    public static HashMap<String, String> getExchangeRate() throws IOException {
        String url_str = "https://v6.exchangerate-api.com/v6/f68d333bf71a315beb2c3272/latest/USD";

        URL url = new URL(url_str);
        HttpURLConnection request = (HttpURLConnection) url.openConnection();
        request.connect();

        JsonParser jp = new JsonParser();
        JsonElement root = jp.parse(new InputStreamReader((InputStream) request.getContent()));
        JsonObject jsonobj = root.getAsJsonObject();
        JsonElement req_result = jsonobj.get("conversion_rates");
        String req_result2 = String.valueOf(jsonobj.get("conversion_rates")).replace("{", "")
                .replace("}", "").replace("\"","");
        HashMap<String, String> hashMap = new HashMap<>();
        HashMap<String, String> newHashMap = new HashMap<>();
        String[] str = req_result2.split(",");
        for (String i : str) {
            String[] usd = i.split(":");
            hashMap.put(usd[0], usd[1]);
        }
        Set<Map.Entry<String, String>> set = hashMap.entrySet();
        Currency[]currency = Currency.values();
        for (Map.Entry<String, String> i : set) {
            for (Currency j : currency) {
                String currencyStr = String.valueOf(j);
                if (i.getKey().equals(currencyStr)) {
                    newHashMap.put(i.getKey(), i.getValue());
                }
            }
        }
        return newHashMap;
    }
    public static BigDecimal convertRate(String firstCurrency, String secondCurrency) throws IOException {
        String url_str = "https://v6.exchangerate-api.com/v6/f68d333bf71a315beb2c3272/pair/" + firstCurrency + "/" + secondCurrency;

        URL url = new URL(url_str);
        HttpURLConnection request = (HttpURLConnection) url.openConnection();
        request.connect();

        JsonParser jp = new JsonParser();
        JsonElement root = jp.parse(new InputStreamReader((InputStream) request.getContent()));
        JsonObject jsonobj = root.getAsJsonObject();
        JsonElement req_result = jsonobj.get("conversion_rate");
        String req_result2 = String.valueOf(jsonobj.get("conversion_rate")).replace("{", "")
                .replace("}", "").replace("\"","");

        return new BigDecimal(req_result2);
    }

}
