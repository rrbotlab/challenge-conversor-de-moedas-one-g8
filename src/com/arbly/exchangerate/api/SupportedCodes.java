package com.arbly.exchangerate.api;

import com.google.gson.annotations.SerializedName;

import java.util.Map;

public record SupportedCodes(
        String result,
        @SerializedName("error-type")
        String errorType,
        @SerializedName("supported_codes")
        Map<String, String> supportedCodesMap) {}
