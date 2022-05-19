package com.example.recipeService;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static org.junit.jupiter.api.Assertions.*;

class RecipeListenerTest {

    RecipeListener recipeListener = new RecipeListener();

    @Test
    void recipeInformationTest() {
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
        assertEquals(recipeDataTestObject.getStore(), recipeListener.recipeInformation(testRecipeData).getStore());
        assertEquals(recipeDataTestObject.getStoreNumber(), recipeListener.recipeInformation(testRecipeData).getStoreNumber());
        assertEquals(recipeDataTestObject.getAddress(), recipeListener.recipeInformation(testRecipeData).getAddress());
        assertEquals(recipeDataTestObject.getDate(), recipeListener.recipeInformation(testRecipeData).getDate());
        assertEquals(recipeDataTestObject.getLineBreaker(), recipeListener.recipeInformation(testRecipeData).getLineBreaker());
        assertEquals(recipeDataTestObject.getOrderData(), recipeListener.recipeInformation(testRecipeData).getOrderData());
    }
}