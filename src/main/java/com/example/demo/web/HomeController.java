package com.example.demo.web;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * HomeController class
 *
 * @author gexc
 * @date 2019/06/05
 */
@RestController
public class HomeController {


    @GetMapping("/token")
    public Map token() {
        return null;
    }
}
