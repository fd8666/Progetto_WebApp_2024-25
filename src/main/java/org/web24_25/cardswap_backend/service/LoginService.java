package org.web24_25.cardswap_backend.service;

import jakarta.servlet.http.HttpSession;
import org.springframework.http.ResponseEntity;
import org.web24_25.cardswap_backend.requests.GoogleLogin;
import org.web24_25.cardswap_backend.requests.PasswordLogin;

import java.util.UUID;

public class LoginService {
    public ResponseEntity<String> loginPassword(PasswordLogin data, HttpSession session) {
        if (data.email() == null || data.password() == null) {
            return ResponseEntity.badRequest().body("Missing fields");
        }
        if (data.email().length() < 3 || data.email().length() > 50) {
            return ResponseEntity.badRequest().body("Email must be between 3 and 50 characters");
        }
        if (data.password().length() < 8 || data.password().length() > 50) {
            return ResponseEntity.badRequest().body("Password must be between 8 and 50 characters");
        }

        String sessionId = UUID.randomUUID().toString();
        session.setAttribute("session_id", sessionId);

        return ResponseEntity.ok("Success");
    }

    public ResponseEntity<String> loginGoogle(GoogleLogin data, HttpSession session) {
        if (data.google_id() == null) {
            return ResponseEntity.badRequest().body("Missing fields");
        }

        String sessionId = UUID.randomUUID().toString();
        session.setAttribute("session_id", sessionId);

        return ResponseEntity.ok("Success");
    }
}
