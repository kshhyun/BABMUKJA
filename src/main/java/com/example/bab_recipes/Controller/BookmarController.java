package com.example.bab_recipes.Controller;

import com.example.bab_recipes.Service.MarkService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/bookmark")
public class BookmarController {

    @Autowired
    private MarkService markService;

    @PostMapping("/add")
    public ResponseEntity<?> addBookmark(@RequestBody Map<String, String> request, HttpSession session) {
        Long userId = (Long) session.getAttribute("userId");
        String recipeId = request.get("recipeId");

        boolean result = markService.addBookmark(userId, recipeId);

        return result ? ResponseEntity.ok().body(Map.of("success", true))
                :ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("success", false));
    }

    @PostMapping("/remove")
    public ResponseEntity<?> removeBookmark(@RequestBody Map<String, String> request, HttpSession session) {
        Long userId = (Long) session.getAttribute("userId");
        String recipeId = request.get("recipeId");

        boolean result = markService.removeBookmark(userId, recipeId);

        return result ? ResponseEntity.ok().body(Map.of("success", true))
                : ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("success", false));
    }
}
