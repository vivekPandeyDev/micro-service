package org.loop.troop.controller;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;


@RestController
public class PublicController {
    
    @GetMapping("/test")
    public String getMethodName() {
        return new String("test string updated");
    }
    
}
