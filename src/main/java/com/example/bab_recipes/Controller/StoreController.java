package com.example.bab_recipes.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class StoreController {

    /*
    Store - store
     */
    @GetMapping("/store")
    public String store(){
        return "/store";
    }
}
