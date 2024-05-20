package bg.softuni.bookshopsystem.services;

import java.io.IOException;

public interface SeedService {
    void seedAuthors() throws IOException;
    void seedBooks() throws IOException;
    void seedCategories() throws IOException;

    //default-ен метод - метод от интерфейса който няма нужда да се пренаписва !!!!!!!!! пита се по интервюта
    default void seedAllData() throws IOException {
        seedCategories();
        seedAuthors();
        seedBooks();
    }
}
