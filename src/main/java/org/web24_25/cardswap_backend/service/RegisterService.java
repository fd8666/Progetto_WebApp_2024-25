package org.web24_25.cardswap_backend.service;

import org.springframework.http.ResponseEntity;
import org.web24_25.cardswap_backend.requests.GoogleRegistration;
import org.web24_25.cardswap_backend.requests.PasswordRegistration;

public class RegisterService {
    public ResponseEntity<String> registerPassword(PasswordRegistration data) {
        if (data.username() == null || data.email() == null || data.password() == null) {
            return ResponseEntity.badRequest().body("Missing fields");
        }
        if (data.username().length() < 3 || data.username().length() > 20) {
            return ResponseEntity.badRequest().body("Username must be between 3 and 20 characters");
        }
        if (data.email().length() < 3 || data.email().length() > 50) {
            return ResponseEntity.badRequest().body("Email must be between 3 and 50 characters");
        }
        if (data.password().length() < 8 || data.password().length() > 50) {
            return ResponseEntity.badRequest().body("Password must be between 8 and 50 characters");
        }
        return ResponseEntity.ok("Success");
    }

    public ResponseEntity<String> registerGoogle(GoogleRegistration data) {
        if (data.google_id() == null) {
            return ResponseEntity.badRequest().body("Missing fields");
        }
        return ResponseEntity.ok("Success");
    }
}
