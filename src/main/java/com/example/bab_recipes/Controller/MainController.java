package com.example.bab_recipes.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {

    @GetMapping("/")
    public String index() {
        return "/main";
    }

//    @GetMapping("/bookmark")
//    public String bookmark() {
//        return "bookmark";
//    }
}
