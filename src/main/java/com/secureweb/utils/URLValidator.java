package com.secureweb.utils;

import java.util.Arrays;
import java.util.List;

public class URLValidator {
    private static final List<String> WHITELISTED_DOMAINS = Arrays.asList("example.com", "api.example.com");

    // Check if the URL domain is allowed
    public static boolean isDomainWhitelisted(String url) {
        try {
            java.net.URL u = new java.net.URL(url);
            String host = u.getHost();
            return WHITELISTED_DOMAINS.contains(host);
        } catch (Exception e) {
            return false;
        }
    }
}