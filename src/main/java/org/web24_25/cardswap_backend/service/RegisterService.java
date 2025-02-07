package org.web24_25.cardswap_backend.service;

import org.springframework.http.ResponseEntity;
import org.web24_25.cardswap_backend.database.postgres_implementation.dbTables.UsersTablePostgres;
import org.web24_25.cardswap_backend.requests.GoogleRegistration;
import org.web24_25.cardswap_backend.requests.PasswordRegistration;

public class RegisterService {
    public ResponseEntity<String> registerPassword(PasswordRegistration data) {
        if (data.username() == null || data.email() == null || data.password() == null) {
            return ResponseEntity.badRequest().body("{\"result\":\"Missing fields\"}");
        }

        if (data.username().length() < 3 || data.username().length() > 20) {
            //Username must be between 3 and 20 characters
            return ResponseEntity.badRequest().body("{\"result\":\"Invalid username\"}");
        }
        if (!data.email().matches("^[\\w-.]+@([\\w-]+\\.)+[\\w-]{2,4}$")) {
            return ResponseEntity.badRequest().body("{\"result\":\"Invalid email\"}");
        }
        if (!data.password().matches("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[\\W_])[a-zA-Z\\d\\W_]{8,}$")) {
            //Password must contain at least one lowercase letter, one uppercase letter, one digit, and be at least 8 characters long
            return ResponseEntity.badRequest().body("{\"result\":\"Invalid password\"}");
        }

        if (UsersTablePostgres.getInstance().createUserWithPassword(data.username(), data.email(), data.password())) {
            return ResponseEntity.ok("{\"result\":\"Success\"}");
        } else {
            return ResponseEntity.badRequest().body("{\"result\":\"Username or email already in use\"}");
        }
    }

    public ResponseEntity<String> registerGoogle(GoogleRegistration data) {
        if (data.google_id() == null) {
            return ResponseEntity.badRequest().body("{\"result\":\"Missing fields\"}");
        }
        return ResponseEntity.ok("{\"result\":\"Success\"}");
    }
}
