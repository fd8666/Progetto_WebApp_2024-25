package org.web24_25.cardswap_backend.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.web24_25.cardswap_backend.requests.SetPassword;
import org.web24_25.cardswap_backend.requests.SetPicture;
import org.web24_25.cardswap_backend.requests.SetUsername;
import org.web24_25.cardswap_backend.service.ProfileService;

@RestController
@CrossOrigin("http://localhost:4200")
@RequestMapping("/api/profile")
public class ProfileController {
    private final ProfileService service = new ProfileService();
    @GetMapping("/usermane")
    public ResponseEntity<String> getUsername(HttpSession session) {
        return service.getUsername(session);
    }

    @GetMapping("/email")
    public ResponseEntity<String> getEmail(HttpSession session) {
        return service.getEmail(session);
    }

    @GetMapping("/picture")
    public ResponseEntity<String> getPicture(HttpSession session) {
        return service.getPicture(session);
    }

    @PostMapping("/set/username")
    public ResponseEntity<String> setUsername(@RequestBody SetUsername data, HttpSession session) {
        return service.setUsername(data, session);
    }

    @PostMapping("/set/password")
    public ResponseEntity<String> setPassword(@RequestBody SetPassword data, HttpSession session) {
        return service.setPassword(data, session);
    }

    @PostMapping("/set/picture")
    public ResponseEntity<String> setPicture(@RequestBody SetPicture data, HttpSession session) {
        return service.setPicture(data, session);
    }
}
