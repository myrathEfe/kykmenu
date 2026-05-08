package com.efe.kykmenusystem.dto;

import com.efe.kykmenusystem.entity.MealType;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

public class MenuSearchCriteria {

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate date;

    private MealType mealType;

    private String mainDish;

    private Integer minCalories;

    private Integer maxCalories;

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public MealType getMealType() {
        return mealType;
    }

    public void setMealType(MealType mealType) {
        this.mealType = mealType;
    }

    public String getMainDish() {
        return mainDish;
    }

    public void setMainDish(String mainDish) {
        this.mainDish = mainDish;
    }

    public Integer getMinCalories() {
        return minCalories;
    }

    public void setMinCalories(Integer minCalories) {
        this.minCalories = minCalories;
    }

    public Integer getMaxCalories() {
        return maxCalories;
    }

    public void setMaxCalories(Integer maxCalories) {
        this.maxCalories = maxCalories;
    }
}
