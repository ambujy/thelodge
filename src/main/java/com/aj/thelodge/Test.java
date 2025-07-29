package com.aj.thelodge;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;


@RestController
public class Test {

    @GetMapping("/")
    public String show() {
        return "Hello World";
    }

}
