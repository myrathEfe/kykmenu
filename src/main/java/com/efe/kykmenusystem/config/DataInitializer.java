package com.efe.kykmenusystem.config;

import com.efe.kykmenusystem.entity.MealType;
import com.efe.kykmenusystem.entity.Menu;
import com.efe.kykmenusystem.entity.Role;
import com.efe.kykmenusystem.entity.User;
import com.efe.kykmenusystem.repository.MenuRepository;
import com.efe.kykmenusystem.repository.UserRepository;
import com.efe.kykmenusystem.service.AuthService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@Component
public class DataInitializer implements CommandLineRunner {

    private final UserRepository userRepository;
    private final MenuRepository menuRepository;
    private final AuthService authService;

    public DataInitializer(UserRepository userRepository, MenuRepository menuRepository, AuthService authService) {
        this.userRepository = userRepository;
        this.menuRepository = menuRepository;
        this.authService = authService;
    }

    @Override
    public void run(String... args) {
        createUserIfMissing("admin", "admin123", Role.ADMIN);
        createUserIfMissing("user", "user123", Role.USER);

        if (menuRepository.count() == 0) {
            menuRepository.saveAll(List.of(
                    sampleMenu(LocalDate.now(), MealType.SABAH, "Mercimek Çorbası", "Peynirli Omlet", "Zeytin - Domates", "Çay", 620, "Güne enerjik başlamak için hazırlanmış örnek sabah menüsü."),
                    sampleMenu(LocalDate.now(), MealType.OGLE, "Ezogelin Çorbası", "Tavuk Sote", "Pirinç Pilavı", "Ayran", 890, "Öğle öğününde dengeli protein ve karbonhidrat içeriği sunar."),
                    sampleMenu(LocalDate.now().plusDays(1), MealType.AKSAM, "Domates Çorbası", "Kuru Fasulye", "Bulgur Pilavı", "Kemalpaşa Tatlısı", 970, "Akşam yemeği için geleneksel ve doyurucu örnek menü.")
            ));
        }
    }

    private void createUserIfMissing(String username, String rawPassword, Role role) {
        if (userRepository.findByUsername(username).isPresent()) {
            return;
        }

        User user = new User();
        user.setUsername(username);
        user.setPasswordHash(authService.encodePassword(rawPassword));
        user.setRole(role);
        userRepository.save(user);
    }

    private Menu sampleMenu(LocalDate date, MealType mealType, String soup, String mainDish, String sideDish,
                            String dessertOrDrink, Integer calories, String description) {
        Menu menu = new Menu();
        menu.setDate(date);
        menu.setMealType(mealType);
        menu.setSoup(soup);
        menu.setMainDish(mainDish);
        menu.setSideDish(sideDish);
        menu.setDessertOrDrink(dessertOrDrink);
        menu.setCalories(calories);
        menu.setDescription(description);
        return menu;
    }
}
