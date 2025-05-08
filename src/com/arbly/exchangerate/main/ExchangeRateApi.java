package com.arbly.exchangerate.main;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import static com.arbly.exchangerate.util.AnsiColors.*;

public class ExchangeRateApi {

    private String baseCurrency;
    private String targetCurrency;
    private final String baseUrl;

    public ExchangeRateApi() {
        String apiKey = System.getenv("API_KEY");
        if (apiKey == null) {
            var msg = """
                    +------------------------+
                    | API_KEY não foi setada |
                    +------------------------+
                    """;
            System.out.println(ANSI_RED + msg + ANSI_RESET);
            System.exit(0);
        }
        this.baseUrl = "https://v6.exchangerate-api.com/v6/" + apiKey;
        this.baseCurrency = "USD";
        this.targetCurrency = "BRL";
    }

    public String getBaseCurrency() {
        return baseCurrency;
    }

    public void setBaseCurrency(String baseCurrency) {
        this.baseCurrency = baseCurrency;
    }

    public String getTargetCurrency() {
        return targetCurrency;
    }

    public void setTargetCurrency(String targetCurrency) {
        this.targetCurrency = targetCurrency;
    }

    private ConversionRates updateConversionRates(String baseCurrency) {
        var requestUrl = this.baseUrl  + "/latest/" + baseCurrency;

        var response = apiRequest(requestUrl);

        if (response != null) {
            Gson gson = new GsonBuilder()
                    .setPrettyPrinting()
                    .create();

            return gson.fromJson(response, ConversionRates.class);
        }else
            return null;
    }

    private SupportedCodes updateSupportedCodes() {
        var requestUrl = this.baseUrl  + "/codes";

        var response = apiRequest(requestUrl);

        if (response != null) {
            Gson gson = new GsonBuilder()
                    .setPrettyPrinting()
                    .create();

            return gson.fromJson(response, SupportedCodes.class);
        }else
            return null;
    }

    private String apiRequest(String requestUrl){
        HttpResponse<String> response;
        try (HttpClient client = HttpClient.newHttpClient()) {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(requestUrl))
                    .build();

            response = client
                    .send(request, HttpResponse.BodyHandlers.ofString());
//                  .sendAsync(request, HttpResponse.BodyHandlers.ofString());
            return response.body();
        } catch (RuntimeException | IOException | InterruptedException e) {
            System.out.println("Fatal: " + e.getMessage());
        }
        return null;
    }

    public Double exchange(Double v, String o, String t) throws IOException, InterruptedException {

        var rates = updateConversionRates(o);
        if (rates != null) {
            if (rates.result().equals("success")) {
                var conversionRate = rates.conversionRatesMap().get(t);
                this.baseCurrency = o;
                this.targetCurrency = t;
                return (v * conversionRate);
            } else {
                System.out.println(ANSI_RED + "API Erro: " + rates.errorType() + ANSI_RESET);
                return null;
            }
        }else
            return null;
    }

    public SupportedCodes supportedCodes(){
        var supportedCodes = updateSupportedCodes();
        if (supportedCodes != null) {
            if (supportedCodes.result().equals("success")) {
                return supportedCodes;
            } else {
                System.out.println(ANSI_RED + "API Erro: " + supportedCodes.errorType() + ANSI_RESET);
                return null;
            }
        }else
            return null;
    }





}
