package com.efe.kykmenusystem.dto;

import com.efe.kykmenusystem.entity.MealType;
import com.efe.kykmenusystem.entity.Menu;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;

public class MenuForm {

    private Long id;

    @NotNull(message = "Tarih boş bırakılamaz.")
    private LocalDate date;

    @NotNull(message = "Öğün türü boş bırakılamaz.")
    private MealType mealType;

    private String soup;

    @NotBlank(message = "Ana yemek boş bırakılamaz.")
    private String mainDish;

    private String sideDish;

    private String dessertOrDrink;

    @NotNull(message = "Kalori bilgisi boş bırakılamaz.")
    @Min(value = 0, message = "Kalori negatif olamaz.")
    private Integer calories;

    private String description;

    private MultipartFile imageFile;

    public static MenuForm fromEntity(Menu menu) {
        MenuForm form = new MenuForm();
        form.setId(menu.getId());
        form.setDate(menu.getDate());
        form.setMealType(menu.getMealType());
        form.setSoup(menu.getSoup());
        form.setMainDish(menu.getMainDish());
        form.setSideDish(menu.getSideDish());
        form.setDessertOrDrink(menu.getDessertOrDrink());
        form.setCalories(menu.getCalories());
        form.setDescription(menu.getDescription());
        return form;
    }

    @AssertTrue(message = "Yüklenen görsel yalnızca JPG veya PNG formatında olabilir.")
    public boolean isImageFileValid() {
        if (imageFile == null || imageFile.isEmpty()) {
            return true;
        }

        String contentType = imageFile.getContentType();
        return "image/jpeg".equalsIgnoreCase(contentType)
                || "image/png".equalsIgnoreCase(contentType);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    public String getSoup() {
        return soup;
    }

    public void setSoup(String soup) {
        this.soup = soup;
    }

    public String getMainDish() {
        return mainDish;
    }

    public void setMainDish(String mainDish) {
        this.mainDish = mainDish;
    }

    public String getSideDish() {
        return sideDish;
    }

    public void setSideDish(String sideDish) {
        this.sideDish = sideDish;
    }

    public String getDessertOrDrink() {
        return dessertOrDrink;
    }

    public void setDessertOrDrink(String dessertOrDrink) {
        this.dessertOrDrink = dessertOrDrink;
    }

    public Integer getCalories() {
        return calories;
    }

    public void setCalories(Integer calories) {
        this.calories = calories;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public MultipartFile getImageFile() {
        return imageFile;
    }

    public void setImageFile(MultipartFile imageFile) {
        this.imageFile = imageFile;
    }
}
