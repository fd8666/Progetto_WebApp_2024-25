package org.web24_25.cardswap_backend.service;

import org.springframework.http.ResponseEntity;
import org.web24_25.cardswap_backend.utils.RSAEncryption;

import java.security.KeyPair;

public class SecurityService {
    public ResponseEntity<String> getPublicKey() {
        KeyPair pair = RSAEncryption.generateKeyPairs();
        assert pair != null;
        return ResponseEntity.ok(RSAEncryption.publicKeyToString(pair.getPublic()));
    }
}
