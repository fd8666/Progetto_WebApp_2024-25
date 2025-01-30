package org.web24_25.cardswap_backend.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.web24_25.cardswap_backend.service.SecurityService;

@RestController
@CrossOrigin("http://localhost:4200")
@RequestMapping("/api/security")
public class SecurityController {
    private final SecurityService service = new SecurityService();

    @GetMapping("/get/publicKey")
    public ResponseEntity<String> getPublicKey() {
        return service.getPublicKey();
    }
}
