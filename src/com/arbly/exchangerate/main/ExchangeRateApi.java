package com.arbly.exchangerate.main;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Scanner;

import static com.arbly.exchangerate.util.AnsiColors.*;

public class ExchangeRateApi {

    private String baseCurrency;
    private String targetCurrency;
    private final String baseUrl;

    public String getBaseUrl() {
        return baseUrl;
    }

    public ExchangeRateApi() {
        String apiKey = System.getenv("EXCHANGERATE_API_KEY");
        if (apiKey == null) {
            Scanner sc = new Scanner(System.in);
            System.out.println(ANSI_RED + """
                    +-----------------------------------------------------------+
                    | Variável de ambiente EXCHANGERATE_API_KEY não foi setada! |
                    +-----------------------------------------------------------+
                    """ + ANSI_RESET);
            System.out.println("Digite a EXCHANGERATE_API_KEY ou pressione ENTER para sair");
            System.out.print("EXCHANGERATE_API_KEY: ");
            apiKey = sc.nextLine();
        }
        if(apiKey.isBlank() || !apiKey.matches("[A-Fa-f0-9]+")){
            if(!apiKey.isBlank())
                System.out.println(ANSI_RED + "EXCHANGERATE_API_KEY formato inválido." + ANSI_RESET);
            System.exit(0);
        }
        this.baseUrl = "https://v6.exchangerate-api.com/v6/" + apiKey;
        this.baseCurrency = "USD";
        this.targetCurrency = "BRL";
    }

    public String getBaseCurrency() {
        return baseCurrency;
    }

    public String getTargetCurrency() {
        return targetCurrency;
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

    public Double exchange(Double v, String o, String t) {
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
