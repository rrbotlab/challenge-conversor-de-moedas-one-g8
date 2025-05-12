package com.arbly.exchangerate.api;

import com.google.gson.annotations.SerializedName;
import java.util.Map;

public record ConversionRates(
        String result,
        @SerializedName("error-type")
        String errorType,
        @SerializedName("base_code")
        String baseCode,
        @SerializedName("conversion_rates")
        Map<String, Double> conversionRatesMap) {}
