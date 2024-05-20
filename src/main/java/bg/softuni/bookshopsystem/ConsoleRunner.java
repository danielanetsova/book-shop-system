package bg.softuni.bookshopsystem;

import bg.softuni.bookshopsystem.services.AuthorService;
import bg.softuni.bookshopsystem.services.BookService;
import bg.softuni.bookshopsystem.services.SeedService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class ConsoleRunner implements CommandLineRunner {
    private final SeedService seedService;
    private final BookService bookService;
    private final AuthorService authorService;

    @Autowired
    public ConsoleRunner(SeedService seedService, BookService bookService, AuthorService authorService) {
        this.seedService = seedService;
        this.bookService = bookService;
        this.authorService = authorService;
    }

    @Override
    //@Transactional - ако сложим тази анотация значи че си разширяваме сесията и не е необходимо да слагаме
    // FetchType.EAGER в @OneToMany на полето books в класа Author
    public void run(String... args) throws Exception {
        seedService.seedAllData();
        //задачи от Spring Data intro ex
        //задача 1
//        bookService.getAllBooksAfterGivenYear(2000).forEach(book ->
//                System.out.println(book.getTitle() + " -> " + book.getReleaseDate()));

        //задача 2
//        authorService.getAuthorsReleasedBooksBeforeGivenYear(2000)
//                .forEach(author -> System.out.println(author.getFullName()));

        //задача 3
//        System.out.println(authorService.getAllAuthorsNamesAndBookCount());

        //задача 4
//        authorService.getBooksInfoByAuthorFullName("George", "Powell");

        //задачи от Advanced Quering ex
        //задача 1 -> Books Titles by Age Restriction
        //bookService.findBooksByAgeRestriction("teEN").forEach(book -> System.out.println(book.getTitle()));
        //тук ако не ми трябват книгите мога просто чрез query да взема заглавията и директно да ги върна

        //задача 2 -> Golden Books
//        bookService.findGoldenEditionBookTitlesThatHaveCopiesLessThan(5000).forEach(System.out::println);

        //задача 3 -> Books by Price
//        bookService.printTitlesAndPricesLowerAndHigherThan(5, 40);

        //задача 4 -> Not Released Books
//        bookService.printTitlesOfBooksNotReleasedInYear(1998);

        //задача 5 -> Books Released Before Date
//        bookService.printBooksReleasedBeforeDate("30-12-1989");

        //задача 6 -> Authors Search
//        authorService.printAuthorFullNameEndingWith("dy");

        //задача 7 -> Books Search
//        bookService.printBookTitlesContainingString("WOR");

        //задача 8 -> Book Titles Search
//        bookService.printTitlesAndAuthorsWithLastNameStarting("gr");

        //задача 9 -> Count Books
//        bookService.printCountOfTitlesLongerThan(40);

        //задача 10 -> Total Book Copies
//        authorService.printTotalBookCopiesPerAuthor();
//       // тази задача може и да се реши с интерфейс или клас това се налага, когато ни е необходимо да ни
//          ни върне точно такъв тип. В случая просто трябва да изптинтим стринговете

        //задача 11 ->  Reduced Book
//        bookService.printBookInformation("Recalled to Life"); - попр ще ни върне 1 книга, но има и повтарящи се
        //заглавия затова си го оставяме лист

        //задача 12 -> Increase Book Copies
//        System.out.println(bookService.returnTotalAddedCopiesOfBooksReleasedAfter("06 Jun 2013", 44));

        //задача 13 -> Remove Books
        //на тази задача за да изтрия книгата трябва да прекъсна връзката й с автора
        //затова най доброто решение е да няма таблица authors_books защото и при наливането на книгите имаше проблеми
        //в базата и сега има
        //решение би било да се добави on delete cascade в самата база
//        bookService.printCountOfDeletedBooksHavingCopiesLessThan(206);

        //задача 14 -> Stored Procedure
        authorService.printCountOfBooksWrittenByAuthor("Amanda Rice");
    }
}
