package com.example.bargainfindermax.controller;

import com.example.bargainfindermax.dto.BargainFinderMaxRequest;
import com.example.bargainfindermax.dto.BargainFinderMaxResponse;
import com.example.bargainfindermax.service.BargainFinderMaxService;
import lombok.Data;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v5/offers")
public class BargainFinderMaxController {

    private final BargainFinderMaxService service;

    public BargainFinderMaxController(BargainFinderMaxService service) {
        this.service = service;
    }

    @PostMapping("/shop")
    public ResponseEntity<BargainFinderMaxResponse> searchFlights(@RequestBody BargainFinderMaxRequest request) {
        BargainFinderMaxResponse response = service.searchFlights(request);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/auth-token")
    public String testServer() {
        return service.getAuthToken();
    }

    @GetMapping("/hello")
    public String hello() {
        return "hello";
    }

}
