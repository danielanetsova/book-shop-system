package bg.softuni.bookshopsystem.services;

import bg.softuni.bookshopsystem.domain.enums.AgeRestriction;
import bg.softuni.bookshopsystem.domain.enums.EditionType;
import bg.softuni.bookshopsystem.domain.models.Author;
import bg.softuni.bookshopsystem.domain.models.Book;
import bg.softuni.bookshopsystem.domain.models.Category;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static bg.softuni.bookshopsystem.constants.FilePath.*;

@Service
public class SeedServiceImpl implements SeedService {
    private final CategoryService categoryService;
    private final BookService bookService;
    private final AuthorService authorService;


    public SeedServiceImpl(CategoryService categoryService, BookService bookService, AuthorService authorService) {
        this.categoryService = categoryService;
        this.bookService = bookService;
        this.authorService = authorService;
    }

    @Override
    public void seedAuthors() throws IOException {
        if (authorService.isDataSeeded()) return;

        authorService.seedAuthors(
                Files
                        .readAllLines(
                                Path.of(RESOURCE_URL + AUTHORS_FILE_NAME))
                        .stream()
                        .map(author -> {
                            String firstName = author.split(" ")[0];
                            String lastName = author.split(" ")[1];
                            return new Author(firstName, lastName);
                        }).toList()
        );
    }

    @Override
    public void seedBooks() throws IOException {
        if (bookService.isDataSeeded()) return;


        Files.readAllLines(Path.of(RESOURCE_URL + BOOKS_FILE_NAME))
                .stream()
                .filter(s -> !s.isBlank())
                .forEach(row -> {
                    Author author = this.authorService.getRandomAuthor();
//                    authorsList.add(author); - добавяме рандъм автор към листа
                    Set<Category> categories = this.categoryService.getRandomCategories();
                    String[] args = row.split("\\s+");
                    String title = Arrays.stream(args).skip(5).collect(Collectors.joining(" "));
                    EditionType editionType = EditionType.values()[Integer.parseInt(args[0])];
                    AgeRestriction ageRestriction = AgeRestriction.values()[Integer.parseInt(args[4])];
                    LocalDate releaseDate = LocalDate.parse(args[1], DateTimeFormatter.ofPattern("d/M/yyyy"));
                    int copies = Integer.parseInt(args[2]);
                    BigDecimal price = new BigDecimal(args[3]);

                    Book book = new Book(title, editionType, price, copies,
                            releaseDate, ageRestriction, author, categories);

                    author.addBook(book); // добавяме книга на автора
                    authorService.updateAuthor(author); //ъпдейтваме автора в базата, тъй като сме му добавили книга
                    // без CascadeType.MERGE гърми, защото реално още не сме добавили никакви книги в базата
                    // а казваме че този автор има онази книга => добавяме CascadeType.MERGE в Author за да ни
                    //създаде книга и да каже че този автор сочи към тази книга. Тоест автоматично ни се добавят книгите
                    // в базата
                    //CascadeType.MERGE: means that related entities are merged
                    //into managed state when the owning entity is merged
                    // Другият вариант е 1-во да си създадем книгите, за които е необходим автор и ги наливаме в базата.
                    // Но проблемът е че authors_books в базата е празна, тъй като не добавяме книгите към
                    // сета от книги на авторите и това се решава с идеята на гушко.
                });

        // идеята на гушко без CascadeType.MERGE в Author класа

        // List<Author> authorsList = new ArrayList<>(); - да създадем лист в който да събираме рандъм авторите

        // List<Book> books = Files.readAllLines(Path.of(RESOURCE_URL + BOOKS_FILE_NAME))
        //                .stream()
        //                .filter(s -> !s.isBlank())
        //                .map(row -> {
        //                    Author author = this.authorService.getRandomAuthor();
        ////                  authorsList.add(author);
        //                    Set<Category> categories = this.categoryService.getRandomCategories();
        //                    String[] args = row.split("\\s+");
        //                    String title = Arrays.stream(args).skip(5).collect(Collectors.joining(" "));
        //                    EditionType editionType = EditionType.values()[Integer.parseInt(args[0])];
        //                    AgeRestriction ageRestriction = AgeRestriction.values()[Integer.parseInt(args[4])];
        //                    LocalDate releaseDate = LocalDate.parse(args[1], DateTimeFormatter.ofPattern("d/M/yyyy"));
        //                    int copies = Integer.parseInt(args[2]);
        //                    BigDecimal price = new BigDecimal(args[3]);
        //
        //                    Book book = new Book(title, editionType, price, copies,
        //                            releaseDate, ageRestriction, author, categories);
        //
        //                    author.addBook(book);
        //                    return book;
        //                }).toList();
        //
        //				bookService.seedBooks(books);
        //              authorService.updateAuthors(authorList);
        // Тук идеята е че отново си добавяме книга на автора, а листът от автори пази референцията на обектите
        // и след като вече книгите са запазени заедно с кореспондиращите им автори в базата тогава ъпдейтваме
        // авторите в базата.

    }

    @Override
    public void seedCategories() throws IOException {
        if (this.categoryService.isDataSeeded()) return;

        //Класът Files помага за четенето и писането на данни
        //В стрийма трябва да се използваме isBlank, защото искаме и да премахнем null-стойностите
        //isBlank проверява дали стрингът няма символи, спейсове и дали е null.
        //isEmpty  проверява дали стрингът няма символи и спейсове.

        this.categoryService.seedCategories(
                Files.
                        readAllLines(Path.of(RESOURCE_URL + CATEGORIES_FILE_NAME))
                        .stream()
                        .filter(category -> !category.isBlank())
                        .map(Category::new)
                        .toList());
    }
}