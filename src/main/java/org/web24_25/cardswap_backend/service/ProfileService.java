package org.web24_25.cardswap_backend.service;

import jakarta.servlet.http.HttpSession;
import org.springframework.http.ResponseEntity;
import org.web24_25.cardswap_backend.requests.SetPassword;
import org.web24_25.cardswap_backend.requests.SetPicture;
import org.web24_25.cardswap_backend.requests.SetUsername;

public class ProfileService {
    public ResponseEntity<String> getUsername(HttpSession session) {
        return ResponseEntity.ok("username "+session.getId());
    }

    public ResponseEntity<String> getEmail(HttpSession session) {
        return ResponseEntity.ok("email "+session.getId());
    }

    public ResponseEntity<String> getPicture(HttpSession session) {
        return ResponseEntity.ok("picture "+session.getId());
    }

    public ResponseEntity<String> setUsername(SetUsername data, HttpSession session) {
        return ResponseEntity.ok("username");
    }

    public ResponseEntity<String> setPassword(SetPassword data, HttpSession session) {
        return ResponseEntity.ok("password");
    }

    public ResponseEntity<String> setPicture(SetPicture data, HttpSession session) {
        return ResponseEntity.ok("picture");
    }
}
