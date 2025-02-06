package org.web24_25.cardswap_backend.requests;

public record AddCard(Integer gameId, Integer expansionId, String cardName, String identifier) { }
