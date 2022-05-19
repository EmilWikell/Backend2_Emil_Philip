package com.example.recipeService;

import lombok.Data;

@Data
public class RecipeData {

    private final String store;
    private final String storeNumber;
    private final String address;
    private final String date;
    private final String lineBreaker;
    private final String orderData;
}
