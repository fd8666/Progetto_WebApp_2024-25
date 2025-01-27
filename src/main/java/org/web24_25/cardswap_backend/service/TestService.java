package org.web24_25.cardswap_backend.service;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class TestService {
    public TestService() {}

    public ResponseEntity<String> getSuccess(String data) {
        return ResponseEntity.ok(data);
    }

    public ResponseEntity<String> getFailed(String data) {
        return ResponseEntity.badRequest().body(data);
    }

    public ResponseEntity<String> postSuccess(String data) {
        return ResponseEntity.ok(data);
    }

    public ResponseEntity<String> postFailed(String data) {
        return ResponseEntity.badRequest().body(data);
    }
}