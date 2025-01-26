package org.web24_25.cardswap_backend.service;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class TestService {

    public TestService() {}

    public ResponseEntity<String> getHelloWorld(Integer id) {
        if(false)
            return ResponseEntity.notFound().build();
        return ResponseEntity.ok("ciao"+id);
    }
}