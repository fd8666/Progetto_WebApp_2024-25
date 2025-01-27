package org.web24_25.cardswap_backend.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.web24_25.cardswap_backend.service.TestService;

@RestController
@CrossOrigin("http://localhost:4200")
@RequestMapping("/api/test")
public class TestController {
    private final TestService ts = new TestService();

    @GetMapping("/success_get/{data}")
    public ResponseEntity<String> success_get(@PathVariable String data){
        return ts.getSuccess(data);
    }

    @GetMapping("/failed_get/{data}")
    public ResponseEntity<String> failed_get(@PathVariable String data){
        return ts.getFailed(data);
    }

    @PostMapping("/success_post")
    public ResponseEntity<String> success_post(@RequestBody String data){
        return ts.postSuccess(data);
    }

    @PostMapping("/failed_post")
    public ResponseEntity<String> failed_post(@RequestBody String data){
        return ts.postFailed(data);
    }
}