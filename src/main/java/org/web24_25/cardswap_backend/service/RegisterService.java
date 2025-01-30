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
            //Username must be between 3 and 20 characters
            return ResponseEntity.badRequest().body("Invalid username");
        }
        if (!data.email().matches("(?=.{1,254}$)[a-z0-9A-Z](?:[a-z0-9A-Z-]{0,61}[a-z0-9A-Z]|)(?:\\.[a-zA-Z]([a-z0-9A-Z-]{0,61}[a-z0-9A-Z]|))*")) {
            return ResponseEntity.badRequest().body("Invalid email");
        }
        if (!data.password().matches("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)[a-zA-Z\\d]{8,}$")) {
            //Password must contain at least one lowercase letter, one uppercase letter, one digit, and be at least 8 characters long
            return ResponseEntity.badRequest().body("Invalid password");
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
