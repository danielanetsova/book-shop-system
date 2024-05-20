package bg.softuni.bookshopsystem.services;

import bg.softuni.bookshopsystem.domain.models.Category;

import java.util.List;
import java.util.Set;

public interface CategoryService {
    boolean isDataSeeded();

    void seedCategories(List<Category> categories);

    Set<Category> getRandomCategories();
}
