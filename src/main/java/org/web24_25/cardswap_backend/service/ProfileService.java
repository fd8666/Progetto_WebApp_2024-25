package org.web24_25.cardswap_backend.service;

import jakarta.servlet.http.HttpSession;
import org.springframework.http.ResponseEntity;
import org.web24_25.cardswap_backend.requests.SetPassword;
import org.web24_25.cardswap_backend.requests.SetPicture;
import org.web24_25.cardswap_backend.requests.SetUsername;

public class ProfileService {
    public ResponseEntity<String> getUsername(HttpSession session) {
        return ResponseEntity.ok("get/username "+session.getId());
    }

    public ResponseEntity<String> getEmail(HttpSession session) {
        return ResponseEntity.ok("get/email "+session.getId());
    }

    public ResponseEntity<String> getPicture(HttpSession session) {
        return ResponseEntity.ok("get/picture "+session.getId());
    }

    public ResponseEntity<String> setUsername(SetUsername data, HttpSession session) {
        String sessionId = (String) session.getAttribute("session_id");
        if (sessionId == null) {
            return ResponseEntity.badRequest().body("Session ID not found");
        }
        return ResponseEntity.ok("set/username "+data.username()+"/"+data.password_hash()+"/"+session.getId());
    }

    public ResponseEntity<String> setPassword(SetPassword data, HttpSession session) {
        String sessionId = (String) session.getAttribute("session_id");
        if (sessionId == null) {
            return ResponseEntity.badRequest().body("Session ID not found");
        }
        return ResponseEntity.ok("set/password "+session.getId());
    }

    public ResponseEntity<String> setPicture(SetPicture data, HttpSession session) {
        String sessionId = (String) session.getAttribute("session_id");
        if (sessionId == null) {
            return ResponseEntity.badRequest().body("Session ID not found");
        }
        return ResponseEntity.ok("set/picture "+session.getId());
    }
}
