package com.study.server.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class HealthController {

    @GetMapping("/test")
    public Map<String, Object> testServer() {
        Map<String, Object> response = new HashMap<>();
        response.put("status", "UP");
        response.put("message", "Server is running correctly ✅");
        response.put("timestamp", System.currentTimeMillis());
        return response;
    }
}
