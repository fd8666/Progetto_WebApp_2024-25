package org.web24_25.cardswap_backend.requests;

public record SetPassword(String password_hash, String new_password_hash) {}
