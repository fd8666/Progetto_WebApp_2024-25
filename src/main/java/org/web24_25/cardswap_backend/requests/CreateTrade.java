package org.web24_25.cardswap_backend.requests;

import java.util.List;

public record CreateTrade(Integer to, String message, List<Integer> offer, List<Integer> request) {}
