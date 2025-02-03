package org.web24_25.cardswap_backend.requests;

import java.util.Base64;

public record SetPicture(String password_hash, String mime_type, String content) {}