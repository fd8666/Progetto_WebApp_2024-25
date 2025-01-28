package org.web24_25.cardswap_backend.requests;

import java.util.Base64;

public record SetPicture(String mime_type, Base64 content) {}