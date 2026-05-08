package com.efe.kykmenusystem.service;

import com.efe.kykmenusystem.dto.MenuForm;
import com.efe.kykmenusystem.dto.MenuSearchCriteria;
import com.efe.kykmenusystem.entity.Menu;
import com.efe.kykmenusystem.exception.ResourceNotFoundException;
import com.efe.kykmenusystem.repository.MenuRepository;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import java.util.Comparator;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

@Service
public class MenuService {

    private final MenuRepository menuRepository;

    public MenuService(MenuRepository menuRepository) {
        this.menuRepository = menuRepository;
    }

    public List<Menu> searchMenus(MenuSearchCriteria criteria) {
        Specification<Menu> specification = Specification.where(null);

        if (criteria.getDate() != null) {
            specification = specification.and((root, query, cb) -> cb.equal(root.get("date"), criteria.getDate()));
        }

        if (criteria.getMealType() != null) {
            specification = specification.and((root, query, cb) -> cb.equal(root.get("mealType"), criteria.getMealType()));
        }

        if (StringUtils.hasText(criteria.getMainDish())) {
            specification = specification.and((root, query, cb) ->
                    cb.like(cb.lower(root.get("mainDish")), "%" + criteria.getMainDish().trim().toLowerCase() + "%"));
        }

        if (criteria.getMinCalories() != null) {
            specification = specification.and((root, query, cb) -> cb.greaterThanOrEqualTo(root.get("calories"), criteria.getMinCalories()));
        }

        if (criteria.getMaxCalories() != null) {
            specification = specification.and((root, query, cb) -> cb.lessThanOrEqualTo(root.get("calories"), criteria.getMaxCalories()));
        }

        return menuRepository.findAll(specification, Sort.by(Sort.Direction.ASC, "date"))
                .stream()
                .sorted(Comparator.comparing(Menu::getDate).thenComparing(Menu::getMealType))
                .toList();
    }

    public List<Menu> findTodayMenus() {
        return menuRepository.findAll((root, query, cb) -> cb.equal(root.get("date"), LocalDate.now()))
                .stream()
                .sorted(Comparator.comparing(Menu::getMealType))
                .toList();
    }

    public long countAllMenus() {
        return menuRepository.count();
    }

    public long countTodayMenus() {
        return menuRepository.countByDate(LocalDate.now());
    }

    public Menu findById(Long id) {
        return menuRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Menü kaydı bulunamadı."));
    }

    @Transactional
    public Menu create(MenuForm form) {
        Menu menu = new Menu();
        mapFormToEntity(form, menu, true);
        return menuRepository.save(menu);
    }

    @Transactional
    public Menu update(Long id, MenuForm form) {
        Menu menu = findById(id);
        mapFormToEntity(form, menu, false);
        return menuRepository.save(menu);
    }

    @Transactional
    public void delete(Long id) {
        Menu menu = findById(id);
        menuRepository.delete(menu);
    }

    public ResponseEntity<byte[]> getMenuImage(Long id) {
        Menu menu = findById(id);
        if (menu.getImageData() == null || menu.getImageData().length == 0) {
            throw new ResourceNotFoundException("Bu menü için görsel bulunamadı.");
        }

        MediaType mediaType = MediaType.parseMediaType(menu.getImageContentType());
        return ResponseEntity.ok()
                .contentType(mediaType)
                .header(HttpHeaders.CACHE_CONTROL, "max-age=3600")
                .body(menu.getImageData());
    }

    private void mapFormToEntity(MenuForm form, Menu menu, boolean isNewEntity) {
        menu.setDate(form.getDate());
        menu.setMealType(form.getMealType());
        menu.setSoup(normalize(form.getSoup()));
        menu.setMainDish(form.getMainDish().trim());
        menu.setSideDish(normalize(form.getSideDish()));
        menu.setDessertOrDrink(normalize(form.getDessertOrDrink()));
        menu.setCalories(form.getCalories());
        menu.setDescription(normalize(form.getDescription()));

        MultipartFile imageFile = form.getImageFile();
        if (imageFile != null && !imageFile.isEmpty()) {
            try {
                menu.setImageData(imageFile.getBytes());
                menu.setImageContentType(imageFile.getContentType());
            } catch (IOException ex) {
                throw new IllegalStateException("Görsel yüklenirken hata oluştu.", ex);
            }
        } else if (isNewEntity) {
            menu.setImageData(null);
            menu.setImageContentType(null);
        }
    }

    private String normalize(String value) {
        return StringUtils.hasText(value) ? value.trim() : null;
    }
}
