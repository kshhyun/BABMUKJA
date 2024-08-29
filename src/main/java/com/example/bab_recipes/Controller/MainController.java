package com.example.bab_recipes.Controller;

import org.springframework.ui.Model;
import com.example.bab_recipes.DTO.RecipeDTO;
import com.example.bab_recipes.Domain.MongoRecipe;
import com.example.bab_recipes.Service.MainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
public class MainController {
    @Autowired
    private MainService mainService;

    @GetMapping("/")
    public String index() {
        return "/main";
    }

    @GetMapping("/pp")
    public String pp() {
        return "popular";
    }

    @GetMapping("/mainSearch")
    public String mainSearch(@RequestParam(value = "query", required = false) String query, Model model) {
        if (query == null || query.trim().isEmpty()) {
            model.addAttribute("recipes", Collections.emptyList());
//            model.addAttribute("limitedIngredients", Collections.emptyList());
            return "/main";
        }

        List<MongoRecipe> recipes = mainService.searchRecipes(query);
        List<RecipeDTO> recipeDTO = recipes.stream()
                .map(recipe -> new RecipeDTO(
                        recipe.getId(),
                        recipe.getTitle(),
                        recipe.getIngredients().entrySet().stream()
                                .limit(5) // 5개 재료만 추출
                                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue)),
                        recipe.getMediaUrl(),
                        false))
                .collect(Collectors.toList());

        model.addAttribute("recipes", recipeDTO);

        return "/main";
    }
}