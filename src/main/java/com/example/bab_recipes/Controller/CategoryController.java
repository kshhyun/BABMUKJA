package com.example.bab_recipes.Controller;

import com.example.bab_recipes.DTO.RecipeDTO;
import com.example.bab_recipes.Domain.MongoRecipe;
import com.example.bab_recipes.Service.CategoryService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
public class CategoryController {

    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping("/recipes")
    public String getRecipesByCategory(Model model) {
        Map<String, List<MongoRecipe>> categoryRecipes = categoryService.getAllRecipesGroupedByCategory();

        // 카테고리별로 RecipeDTO 리스트를 생성하여 모델에 추가
        Map<String, List<RecipeDTO>> recipes = categoryRecipes.entrySet().stream()
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        entry -> entry.getValue().stream()
                                .map(recipe -> new RecipeDTO(
                                        recipe.getId(),
                                        recipe.getTitle(),
                                        recipe.getIngredients(),
                                        recipe.getMediaUrl(),
                                        false))
                                .collect(Collectors.toList())
                ));

        model.addAttribute("category", recipes);
        return "main";
    }

    @GetMapping("/recipes/{categoryName}")
    public String getRecipesByCategory(@PathVariable("categoryName") String categoryName, Model model) {
        // 특정 카테고리의 레시피 가져오기
        List<MongoRecipe> recipesInCategory = categoryService.getAllRecipesGroupedByCategory().get(categoryName);

        if (recipesInCategory == null) {
            recipesInCategory = Collections.emptyList();
        }

        List<RecipeDTO> recipeDTOs = recipesInCategory.stream()
                .map(recipe -> new RecipeDTO(
                        recipe.getId(),
                        recipe.getTitle(),
                        recipe.getIngredients().entrySet().stream()
                                .limit(5)
                                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue)),
                        recipe.getMediaUrl(),
                        false))
                .collect(Collectors.toList());

        model.addAttribute("categories", recipeDTOs);

        return "main"; // 특정 카테고리의 레시피를 출력할 페이지
    }
}
