package bg.softuni.bookshopsystem.services;

import bg.softuni.bookshopsystem.domain.models.Author;
import bg.softuni.bookshopsystem.domain.models.Book;

import java.util.List;

public interface AuthorService {
    boolean isDataSeeded();

    void seedAuthors(List<Author> authors);

    Author getRandomAuthor();

    void updateAuthors(List<Author> authors);

    void updateAuthor(Author author);

    List<Author> getAuthorsReleasedBooksBeforeGivenYear(int year);

    String getAllAuthorsNamesAndBookCount();

    List<Book> getAllBooksByAuthorFullName(String firstName, String lastName);

    void getBooksInfoByAuthorFullName(String firstName, String lastName);

    void printAuthorFullNameEndingWith(String ending);

    void printTotalBookCopiesPerAuthor();

    void  printCountOfBooksWrittenByAuthor(String authorFullName);
}
