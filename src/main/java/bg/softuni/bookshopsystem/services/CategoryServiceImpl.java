package bg.softuni.bookshopsystem.services;

import bg.softuni.bookshopsystem.domain.models.Category;
import bg.softuni.bookshopsystem.repositories.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

@Service
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;

    @Autowired
    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public boolean isDataSeeded() {
        return categoryRepository.count() > 0;
    }

    @Override
    public void seedCategories(List<Category> categories) {
        this.categoryRepository.saveAllAndFlush(categories);
    }

   @Override
   public Category getRandomCategory() {
       long categoriesCount = categoryRepository.count();

       if(categoriesCount > 0) {
           long randomId = new Random().nextLong(1, categoriesCount + 1L);
           return categoryRepository
                   .findById(randomId)
                   .orElseThrow(() -> new IllegalArgumentException("No such category"));
       }

       throw new RuntimeException();
   }

    public Set<Category> getRandomCategories() {
        Set<Category> categories = new HashSet<>();
        long categoriesCount = categoryRepository.count();

        if(categoriesCount > 0) {
            long randomId = new Random().nextLong(1, categoriesCount + 1L);
           categories.add(categoryRepository
                    .findById(randomId)
                    .orElseThrow(() -> new IllegalArgumentException("No such category")));
        }
        return categories;
    }
}
