package org.web24_25.cardswap_backend.service;

import jakarta.servlet.http.HttpSession;
import org.springframework.http.ResponseEntity;
import org.web24_25.cardswap_backend.database.postgres_implementation.dbTables.SessionsTablePostgres;
import org.web24_25.cardswap_backend.database.postgres_implementation.dbTables.UsersTablePostgres;
import org.web24_25.cardswap_backend.database.structure.dbEntry.SessionEntry;
import org.web24_25.cardswap_backend.database.structure.dbEntry.UserEntry;
import org.web24_25.cardswap_backend.requests.SetPassword;
import org.web24_25.cardswap_backend.requests.SetPicture;
import org.web24_25.cardswap_backend.requests.SetUsername;
import org.web24_25.cardswap_backend.utils.GzipCompression;

import java.io.IOException;

public class ProfileService {
    public ResponseEntity<String> logOut(HttpSession session) {
        SessionEntry sessionEntry = SessionsTablePostgres.getInstance().getSessionWithCookie(session.getAttribute("session_id").toString());
        sessionEntry.invalidate();
        session.invalidate();
        return ResponseEntity.ok("Logged out");
    }

    public ResponseEntity<String> getUsername(HttpSession session) {
        if (session.getAttribute("session_id") == null) {
            return ResponseEntity.badRequest().body("{\"result\":\"Session not found\"}");
        }
        SessionEntry sessionEntry = SessionsTablePostgres.getInstance().getSessionWithCookie(session.getAttribute("session_id").toString());
        if (sessionEntry == null) {
            return ResponseEntity.badRequest().body("{\"result\":\"Session not found\"}");
        }
        UserEntry userEntry = UsersTablePostgres.getInstance().getUserWithId(sessionEntry.user_id());
        if (userEntry == null) {
            return ResponseEntity.badRequest().body("{\"result\":\"User not found\"}");
        }
        return ResponseEntity.ok("{\"username\":\""+userEntry.username()+"\"}");
    }

    public ResponseEntity<String> getEmail(HttpSession session) {
        if (session.getAttribute("session_id") == null) {
            return ResponseEntity.badRequest().body("{\"result\":\"Session not found\"}");
        }
        SessionEntry sessionEntry = SessionsTablePostgres.getInstance().getSessionWithCookie(session.getAttribute("session_id").toString());
        if (sessionEntry == null) {
            return ResponseEntity.badRequest().body("{\"result\":\"Session not found\"}");
        }
        UserEntry userEntry = UsersTablePostgres.getInstance().getUserWithId(sessionEntry.user_id());
        if (userEntry == null) {
            return ResponseEntity.badRequest().body("{\"result\":\"User not found\"}");
        }
        return ResponseEntity.ok("{\"email\":\""+userEntry.email()+"\"}");
    }

    public ResponseEntity<String> getPicture(HttpSession session) {
        if (session.getAttribute("session_id") == null) {
            return ResponseEntity.badRequest().body("Session ID not found");
        }
        SessionEntry sessionEntry = SessionsTablePostgres.getInstance().getSessionWithCookie(session.getAttribute("session_id").toString());
        if (sessionEntry == null) {
            return ResponseEntity.badRequest().body("Session not found");
        }
        UserEntry userEntry = UsersTablePostgres.getInstance().getUserWithId(sessionEntry.user_id());
        if (userEntry == null) {
            return ResponseEntity.badRequest().body("User not found");
        }
        return ResponseEntity.ok("picture");
    }

    public ResponseEntity<String> setUsername(SetUsername data, HttpSession session) {
        String sessionId = (String) session.getAttribute("session_id");
        if (sessionId == null) {
            return ResponseEntity.badRequest().body("{\"result\":\"Session not found\"}");
        }
        if (data.username() == null || data.password_hash() == null) {
            return ResponseEntity.badRequest().body("{\"result\":\"Missing fields\"}");
        }

        SessionEntry sessionEntry = SessionsTablePostgres.getInstance().getSessionWithCookie(sessionId);
        if (sessionEntry == null) {
            return ResponseEntity.badRequest().body("{\"result\":\"Session not found\"}");
        }
        UserEntry userEntry = UsersTablePostgres.getInstance().getUserWithId(sessionEntry.user_id());
        if (userEntry == null) {
            return ResponseEntity.badRequest().body("{\"result\":\"User not found\"}");
        }
        if (!userEntry.password_hash().equals(data.password_hash())) {
            return ResponseEntity.badRequest().body("{\"result\":\"Invalid request\"}");
        }
        userEntry.setUsername(data.username());
        return ResponseEntity.ok("{\"result\":\"Success\"}");
    }

    public ResponseEntity<String> setPassword(SetPassword data, HttpSession session) {
        String sessionId = (String) session.getAttribute("session_id");
        if (sessionId == null) {
            return ResponseEntity.badRequest().body("{\"result\":\"Session not found\"}");
        }
        if (data.new_password_hash() == null || data.password_hash() == null) {
            return ResponseEntity.badRequest().body("{\"result\":\"Missing fields\"}");
        }
        SessionEntry sessionEntry = SessionsTablePostgres.getInstance().getSessionWithCookie(sessionId);
        if (sessionEntry == null) {
            return ResponseEntity.badRequest().body("{\"result\":\"Session not found\"}");
        }
        UserEntry userEntry = UsersTablePostgres.getInstance().getUserWithId(sessionEntry.user_id());
        if (userEntry == null) {
            return ResponseEntity.badRequest().body("{\"result\":\"User not found\"}");
        }
        if (!userEntry.password_hash().equals(data.password_hash())) {
            return ResponseEntity.badRequest().body("{\"result\":\"Invalid request\"}");
        }
        userEntry.setPasswordHash(data.new_password_hash());
        return ResponseEntity.ok("{\"result\":\"Success\"}");
    }

    public ResponseEntity<String> setPicture(SetPicture data, HttpSession session) {
        String sessionId = (String) session.getAttribute("session_id");
        if (sessionId == null) {
            return ResponseEntity.badRequest().body("{\"result\":\"Session not found\"}");
        }
        if (data.mime_type() == null || data.content() == null || data.password_hash() == null) {
            return ResponseEntity.badRequest().body("Missing fields");
        }
        SessionEntry sessionEntry = SessionsTablePostgres.getInstance().getSessionWithCookie(sessionId);
        if (sessionEntry == null) {
            return ResponseEntity.badRequest().body("{\"result\":\"Session not found\"}");
        }
        UserEntry userEntry = UsersTablePostgres.getInstance().getUserWithId(sessionEntry.user_id());
        if (userEntry == null) {
            return ResponseEntity.badRequest().body("{\"result\":\"User not found\"}");
        }
        if (!userEntry.password_hash().equals(data.password_hash())) {
            return ResponseEntity.badRequest().body("{\"result\":\"Invalid request\"}");
        }
        try {
            var image = GzipCompression.decompressFromBase64(data.content());
            //TODO: Save image
            return ResponseEntity.ok("{\"result\":\"Success\"}");
        } catch (IOException e) {
            return ResponseEntity.badRequest().body("{\"result\":\"Invalid request\"}");
        }
    }
}
