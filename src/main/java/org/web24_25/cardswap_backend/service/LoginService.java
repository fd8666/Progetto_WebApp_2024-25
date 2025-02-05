package org.web24_25.cardswap_backend.service;

import jakarta.servlet.http.HttpSession;
import org.springframework.http.ResponseEntity;
import org.web24_25.cardswap_backend.database.postgres_implementation.dbTables.SessionsTablePostgres;
import org.web24_25.cardswap_backend.database.postgres_implementation.dbTables.UsersTablePostgres;
import org.web24_25.cardswap_backend.database.structure.dbEntry.UserEntry;
import org.web24_25.cardswap_backend.requests.GoogleLogin;
import org.web24_25.cardswap_backend.requests.PasswordLogin;

import java.util.UUID;

public class LoginService {
    public ResponseEntity<String> loginPassword(PasswordLogin data, HttpSession session) {
        if (data.email() == null || data.password() == null) {
            session.invalidate();
            return ResponseEntity.badRequest().body("Missing fields");
        }

        UserEntry user = UsersTablePostgres.getInstance().getUserFromEmail(data.email());
        if (user == null) {
            session.invalidate();
            return ResponseEntity.badRequest().body("User not found");
        }

        String sessionId = UUID.randomUUID().toString();
        if (SessionsTablePostgres.getInstance().createSession(user.id(), sessionId)) {
            session.setAttribute("session_id", sessionId);
            return ResponseEntity.ok("Success");
        } else {
            session.invalidate();
            return ResponseEntity.badRequest().body("Invalid credentials");
        }
    }

    public ResponseEntity<String> loginGoogle(GoogleLogin data, HttpSession session) {
        if (data.google_id() == null) {
            return ResponseEntity.badRequest().body("Missing fields");
        }

        UserEntry user = UsersTablePostgres.getInstance().getUserFromGoogleId(data.google_id());
        if (user == null) {
            session.invalidate();
            return ResponseEntity.badRequest().body("User not found");
        }

        String sessionId = UUID.randomUUID().toString();
        if (SessionsTablePostgres.getInstance().createSession(user.id(), sessionId)) {
            session.setAttribute("session_id", sessionId);
            return ResponseEntity.ok("Success");
        } else {
            session.invalidate();
            return ResponseEntity.badRequest().body("Invalid credentials");
        }
    }
}
