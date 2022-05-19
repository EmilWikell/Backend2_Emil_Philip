package com.example.recipeService;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static org.junit.jupiter.api.Assertions.*;

class RecipeListenerTest {

    RecipeListener recipeListener = new RecipeListener();

    @Test
    void createRecipeInformationTest() {
        String testRecipeData = "Anneli Kaka";
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        RecipeData recipeDataTestObject = new RecipeData(
                "Store: web-shop",
                "Store number: 154856-4848",
                "Address: Online",
                "Date: " + dtf.format(now),
                "--------------------------------------------------------------------",
                testRecipeData);
        assertEquals(recipeDataTestObject.getStore(), recipeListener.createRecipeInformation(testRecipeData).getStore());
        assertEquals(recipeDataTestObject.getStoreNumber(), recipeListener.createRecipeInformation(testRecipeData).getStoreNumber());
        assertEquals(recipeDataTestObject.getAddress(), recipeListener.createRecipeInformation(testRecipeData).getAddress());
        assertEquals(recipeDataTestObject.getDate(), recipeListener.createRecipeInformation(testRecipeData).getDate());
        assertEquals(recipeDataTestObject.getLineBreaker(), recipeListener.createRecipeInformation(testRecipeData).getLineBreaker());
        assertEquals(recipeDataTestObject.getOrderData(), recipeListener.createRecipeInformation(testRecipeData).getOrderData());
    }
}