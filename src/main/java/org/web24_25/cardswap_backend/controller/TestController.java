package org.web24_25.cardswap_backend.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.web24_25.cardswap_backend.service.TestService;

@RestController
@CrossOrigin("http://localhost:4200")
@RequestMapping("/api/test")
@RequiredArgsConstructor //crea un'istanza di TestService
public class TestController {
    private final TestService ts;

    @GetMapping("/helloWorld/{id}")
    public ResponseEntity<String> findByID(@PathVariable Integer id){
        return ts.getHelloWorld(id);
    }
}