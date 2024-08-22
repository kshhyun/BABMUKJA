package com.example.bab_recipes.Controller;

import com.example.bab_recipes.Domain.MongoRecipe;
import com.example.bab_recipes.Service.MarkService;
import com.example.bab_recipes.Service.TodayService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Controller
public class TodayController {

    @Autowired
    private TodayService todayService;

    @Autowired
    private MarkService markService;
    /*
    Today - today_eat / eat_detail
     */
    @GetMapping("/Today")
    public String Today_eat() {

        return "/Today_eat";
    }

    @PostMapping("/search")
    public String todayEatSearch(@RequestParam(required = false) String fridgeTags,
                                 @RequestParam(required = false) String excludeTags,
                                 HttpSession session) {
        session.removeAttribute("fridgeTags");
        session.removeAttribute("excludeTags");
        session.removeAttribute("recipes");

        String[] fridgeItemsArray = {};
        String[] excludeItemsArray = {};

        if (fridgeTags != null && !fridgeTags.isEmpty()) {
            fridgeItemsArray = fridgeTags.split(",");
        }

        if (excludeTags == null || excludeTags.isEmpty()) {
            System.out.println("if (searchOne): " + fridgeTags);
            List<MongoRecipe> recipes = todayService.searchOne(fridgeItemsArray);

            session.setAttribute("fridgeItems", Arrays.asList(fridgeItemsArray));
            session.setAttribute("recipes", recipes);
            return "redirect:/Today_eat_detail";
        } else {
            System.out.println("else (searchRecipes): " + fridgeTags + ", " + excludeTags);
            excludeItemsArray = excludeTags.split(",");
            List<MongoRecipe> recipes = todayService.searchRecipes(fridgeItemsArray, excludeItemsArray);

            session.setAttribute("fridgeItems", Arrays.asList(fridgeItemsArray));
            session.setAttribute("excludedItems", Arrays.asList(excludeItemsArray));
            session.setAttribute("recipes", recipes);
            return "redirect:/Today_eat_detail";
        }
    }

    @GetMapping("/Today_eat_detail")
    public String Today_eatDetail(Model model, HttpSession session) {
        List<String> fridgeItems = (List<String>) session.getAttribute("fridgeItems");
        List<String> excludeItems = (List<String>) session.getAttribute("excludedItems");
        List<MongoRecipe> recipes = (List<MongoRecipe>) session.getAttribute("recipes");

        model.addAttribute("fridgeItems", fridgeItems);
        model.addAttribute("excludeItems", excludeItems);
        model.addAttribute("recipes", recipes);
        return "/Today_eat_detail";
    }


    @GetMapping("/recipe/detail/{recipeId}")
    public String recipeDetail(@PathVariable("recipeId") Optional<String> recipeId, RedirectAttributes redirectAttributes) {
        String Id = Optional.of(recipeId.orElse("")).orElse("");

        Optional<MongoRecipe> recipe = todayService.detailRecipe(Id);

        System.out.println(recipe.get().getIngredients().keySet().toString());
        System.out.println(recipe.get().getIngredients().values());
        System.out.println(recipe.get().getSteps().keySet().toString());
        System.out.println(recipe.get().getSteps().values());
        redirectAttributes.addFlashAttribute("recipe", recipe);
        return "redirect:/Recipes_detail";

    }

    @GetMapping("/Recipes_detail")
    public String recipeDetail(Model model,HttpSession session) {
        Optional<MongoRecipe> recipe = (Optional<MongoRecipe>) model.asMap().get("recipe");

        Long userId = (Long) session.getAttribute("userId");

        model.addAttribute("recipe", recipe.get());
        return "Recipes_detail";
    }
}
